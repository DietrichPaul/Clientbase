package de.dietrichpaul.clientbase.features.rotation;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.*;
import de.dietrichpaul.clientbase.event.rotate.RotateGetEvent;
import de.dietrichpaul.clientbase.event.rotate.RotateSetEvent;
import de.dietrichpaul.clientbase.event.rotate.SendPitchEvent;
import de.dietrichpaul.clientbase.event.rotate.SendYawEvent;
import de.dietrichpaul.clientbase.features.rotation.strafe.StrafeMode;
import de.dietrichpaul.clientbase.util.math.rtx.Raytrace;
import de.dietrichpaul.clientbase.util.math.rtx.RaytraceUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RotationEngine {

    private Set<RotationSpoof> spoofs = new HashSet<>();

    private float[] prevRotations = new float[2];
    private float[] reportedRotations = new float[2];
    private float[] rotations = new float[2];
    private boolean rotating;
    private boolean hasTarget;
    private boolean confirmedClientRotation = true;

    private boolean raytrace;
    private boolean rotateBack;
    private boolean lockView;
    private SensitivityFix sensitivityFix;
    private StrafeMode strafeMode;

    private float yawSpeed = Float.NaN;
    private float pitchSpeed = Float.NaN;
    private float prevYawSpeed;
    private float prevPitchSpeed;

    private double partialIterations;

    private RotationSpoof prevSpoof;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public RotationEngine() {
        EventManager.register(this);
    }

    public void add(RotationSpoof spoof) {
        this.spoofs.add(spoof);
    }

    private void rotate(boolean tick, float partialTicks) {
        RotationSpoof spoof = prevSpoof;
        if (tick) {
            raytrace = spoof.raytrace();
            strafeMode = spoof.getStrafeMode();
            sensitivityFix = spoof.getSensitivityFix();
            lockView = spoof.lockView();
            rotateBack = spoof.rotateBack();
            prevYawSpeed = yawSpeed;
            prevPitchSpeed = pitchSpeed;
            yawSpeed = spoof.getYawSpeed();
            pitchSpeed = spoof.getPitchSpeed();

            if (Float.isNaN(prevYawSpeed))
                prevYawSpeed = yawSpeed;
            if (Float.isNaN(prevPitchSpeed))
                prevPitchSpeed = pitchSpeed;
        }

        if (hasTarget) {
            float[] targetRotations = new float[2];
            spoof.rotate(targetRotations, tick, partialTicks);
            limitDeltaRotation(reportedRotations, targetRotations,
                    MathHelper.lerp(partialTicks, prevYawSpeed, yawSpeed),
                    MathHelper.lerp(partialTicks, prevPitchSpeed, pitchSpeed),
                    partialTicks
            );
            mouseSensitivity(reportedRotations, targetRotations, rotations, partialTicks);
            if (lockView) {
                MinecraftClient.getInstance().player.setYaw(rotations[0]);
                MinecraftClient.getInstance().player.setPitch(rotations[1]);
            }
            confirmedClientRotation = false;
        } else if (!confirmedClientRotation) {
            if (lockView) {
                if (tick) {
                    confirmClientRotation();
                }
            } else {
                float yawDiff = MathHelper.angleBetween(mc.player.getYaw(), reportedRotations[0]);
                float pitchDiff = MathHelper.angleBetween(mc.player.getPitch(), reportedRotations[1]);
                if (!rotateBack || (tick && yawDiff <= yawSpeed && pitchDiff < pitchSpeed)) {
                    confirmClientRotation();
                } else {
                    float[] targetRotations = new float[2];
                    targetRotations[0] = mc.player.getYaw();
                    targetRotations[1] = mc.player.getPitch();
                    limitDeltaRotation(reportedRotations, targetRotations,
                            MathHelper.lerp(partialTicks, prevYawSpeed, yawSpeed),
                            MathHelper.lerp(partialTicks, prevPitchSpeed, pitchSpeed),
                            partialTicks
                    );
                    mouseSensitivity(reportedRotations, targetRotations, rotations, partialTicks);
                }
            }
        }
    }

    private void confirmClientRotation() {
        float[] targetRotations = new float[]{
                mc.player.getYaw(),
                mc.player.getPitch()
        };
        mouseSensitivity(reportedRotations, targetRotations, rotations, 1.0F);
        mc.player.setYaw(rotations[0]);
        mc.player.getPitch(rotations[1]);
        confirmedClientRotation = true;
    }

    @EventTarget
    public void onSendYaw(SendYawEvent event) {
        if (rotating)
            event.setYaw(rotations[0]);
    }

    @EventTarget
    public void onSendPitch(SendPitchEvent event) {
        if (rotating)
            event.setPitch(rotations[1]);
    }

    @EventTarget
    public void onRaytrace(RaytraceEvent event) {
        if (rotating) {
            event.setCancelled(true);
            Raytrace result;
            if (!raytrace && hasTarget) {
                result = prevSpoof.getTarget();
            } else {
                result = RaytraceUtil.raytrace(mc, mc.getCameraEntity(), rotations, prevRotations, prevSpoof.getRange(), event.getTickDelta());
            }
            mc.targetedEntity = result.target();
            mc.crosshairTarget = result.hitResult();
        }
    }

    @EventTarget
    public void onStrafeInput(StrafeInputEvent event) {
        if (strafeMode.getCorrectMovement() != null) {
            if (rotating) strafeMode.getCorrectMovement().edit(rotations[0], event);
            else strafeMode.getCorrectMovement().reset();
        }
    }

    @EventTarget
    public void onRotateGet(RotateGetEvent event) {
        event.setYaw(rotations[0]);
        event.setPitch(rotations[1]);
    }

    @EventTarget
    public void onRotationSet(RotateSetEvent event) {
        if (event.isYaw())
            rotations[0] = event.getYaw();
        if (event.isPitch())
            rotations[1] = event.getPitch();
    }

    @EventTarget
    public void onJump(JumpEvent event) {
        if (rotating) {
            event.setYaw(rotations[0]);
        }
    }

    @EventTarget
    public void onStrafe(StrafeEvent event) {
        if (rotating && strafeMode.isFixYaw()) {
            event.setYaw(rotations[0]);
        }
    }

    @EventTarget
    public void onMoveCamera(MoveCameraEvent event) {
        if (rotating) {
            if (lockView) {
                rotate(false, event.getTickDelta());
            }
        } else {
            rotations[0] = mc.player.getYaw();
            rotations[1] = mc.player.getPitch();
        }
    }

    @EventTarget
    public void onPreTickRaytrace(PreTickRaytraceEvent event) {
        prevRotations[0] = rotations[0];
        prevRotations[1] = rotations[1];
        Optional<RotationSpoof> spoofOpt = spoofs.stream()
                .filter(RotationSpoof::isToggled)
                .filter(RotationSpoof::pickTarget)
                .max(Comparator.comparingInt(RotationSpoof::getPriority));

        hasTarget = spoofOpt.isPresent();
        RotationSpoof spoof = hasTarget ? spoofOpt.get() : prevSpoof;
        rotating = spoof != null && (hasTarget || !confirmedClientRotation);


        if (rotating) {
            prevSpoof = spoof;
            rotate(true, 1.0F);
        } else {
            yawSpeed = Float.NaN;
            pitchSpeed = Float.NaN;
            rotations[0] = mc.player.getYaw();
            rotations[1] = mc.player.getPitch();
        }
        reportedRotations[0] = rotations[0];
        reportedRotations[1] = rotations[1];
    }

    public void limitDeltaRotation(float[] from, float[] to, float limitYaw, float limitPitch, float partialTicks) {
        to[0] = from[0] + MathHelper.clamp(MathHelper.subtractAngles(from[0], to[0]), -limitYaw * partialTicks, limitYaw * partialTicks);
        to[1] = from[1] + MathHelper.clamp(MathHelper.subtractAngles(from[1], to[1]), -limitPitch * partialTicks, limitPitch * partialTicks);
    }

    public void mouseSensitivity(float[] from, float[] to, float[] server, float partialTicks) {
        double sensitivity = mc.options.getMouseSensitivity().getValue() * 0.6F + 0.2F;
        double sensitivityCb = sensitivity * sensitivity * sensitivity;
        double gcd = sensitivityCb * 8.0;

        float deltaYaw = MathHelper.subtractAngles(from[0], to[0]);
        float deltaPitch = MathHelper.subtractAngles(from[1], to[1]);

        switch (sensitivityFix) {
            case NONE -> {
                server[0] = from[0] + deltaYaw;
                server[1] = from[1] + deltaPitch;
                server[1] = MathHelper.clamp(server[1], -90F, 90F);
            }
            case TICK_BASED -> {
                server[0] = from[0];
                server[1] = from[1];
                float gcdCursorDeltaX = deltaYaw / 0.15F;
                float gcdCursorDeltaY = deltaPitch / 0.15F;

                double cursorDeltaX = gcdCursorDeltaX / gcd;
                double cursorDeltaY = gcdCursorDeltaY / gcd;

                int roundedCursorDeltaX = (int) Math.round(cursorDeltaX);
                int roundedCursorDeltaY = (int) Math.round(cursorDeltaY);

                cursorDeltaX = roundedCursorDeltaX * gcd;
                cursorDeltaY = roundedCursorDeltaY * gcd;

                gcdCursorDeltaX = (float) cursorDeltaX * 0.15F;
                gcdCursorDeltaY = (float) cursorDeltaY * 0.15F;

                server[0] += gcdCursorDeltaX;
                server[1] += gcdCursorDeltaY;
            }
            case APPROXIMATE, REAL -> {
                double iterationsNeeded = (sensitivityFix == SensitivityFix.APPROXIMATE ?
                        ThreadLocalRandom.current().nextDouble(20, 60) : ClientBase.getInstance().getFps())
                        / 20.0;
                iterationsNeeded *= partialTicks;
                int iterations = MathHelper.floor(iterationsNeeded + partialIterations);
                partialIterations += iterationsNeeded - iterations;

                server[0] = from[0];
                server[1] = from[1];

                float gcdCursorDeltaX = deltaYaw / 0.15F;
                float gcdCursorDeltaY = deltaPitch / 0.15F;

                double cursorDeltaX = gcdCursorDeltaX / gcd;
                double cursorDeltaY = gcdCursorDeltaY / gcd;

                double partialDeltaX = 0;
                double partialDeltaY = 0;

                for (int i = 0; i < iterations; i++) {
                    double sollDeltaX = cursorDeltaX / iterations;
                    double sollDeltaY = cursorDeltaY / iterations;

                    int istDeltaX = (int) Math.round(sollDeltaX + partialDeltaX);
                    int istDeltaY = (int) Math.round(sollDeltaY + partialDeltaY);

                    partialDeltaX += sollDeltaX - istDeltaX;
                    partialDeltaY += sollDeltaY - istDeltaY;

                    double newCursorDeltaX = istDeltaX * gcd;
                    double newCursorDeltaY = istDeltaY * gcd;

                    server[0] += (float) newCursorDeltaX * 0.15F;
                    server[1] += (float) newCursorDeltaY * 0.15F;
                }
            }
        }
    }

    public float getPrevYaw() {
        return prevRotations[0];
    }

    public float getPrevPitch() {
        return prevRotations[1];
    }

    public float getYaw() {
        return rotations[0];
    }

    public float getPitch() {
        return rotations[1];
    }

    public boolean isRotating() {
        return rotating;
    }
}
