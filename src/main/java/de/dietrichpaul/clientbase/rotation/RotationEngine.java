package de.dietrichpaul.clientbase.rotation;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.dietrichpaul.clientbase.event.MoveCameraEvent;
import de.dietrichpaul.clientbase.event.PreTickRaytraceEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RotationEngine {

    private Set<RotationSpoof> spoofs = new HashSet<>();

    private float[] prevRotations = new float[2];
    private float[] rotations = new float[2];
    private boolean rotating;
    private boolean hasTarget;
    private boolean confirmedClientRotation = true;

    private RotationSpoof prevSpoof;

    private boolean canInterpolate;

    public RotationEngine() {
        EventManager.register(this);
    }

    public void add(RotationSpoof spoof) {
        this.spoofs.add(spoof);
    }

    private void rotate(boolean tick, float partialTicks) {
        RotationSpoof spoof = prevSpoof;
        if (hasTarget) {
            float[] targetRotations = new float[2];
            spoof.rotate(targetRotations, tick, partialTicks);
            MinecraftClient.getInstance().player.setYaw(targetRotations[0]);
            MinecraftClient.getInstance().player.setPitch(targetRotations[1]);
        }
    }

    @EventTarget
    public void onMoveCamera(MoveCameraEvent event) {
        if (rotating) {
            rotate(false, event.getTickDelta());
        }
    }

    @EventTarget
    public void onPreTickRaytrace(PreTickRaytraceEvent event) {
        Optional<RotationSpoof> spoofOpt = spoofs.stream()
                .filter(RotationSpoof::isToggled)
                .filter(RotationSpoof::pickTarget)
                .max(Comparator.comparingInt(RotationSpoof::getPriority));

        hasTarget = spoofOpt.isPresent();
        RotationSpoof spoof = hasTarget ? spoofOpt.get() : prevSpoof;
        rotating = spoof != null && (hasTarget || !confirmedClientRotation);

        prevRotations[0] = rotations[0];
        prevRotations[1] = rotations[1];

        if (rotating) {
            prevSpoof = spoof;
            rotate(true, 1.0F);
        }
    }

    public void limitDeltaRotation(float[] from, float[]to, float limit) {
        to[0] = from[0] + MathHelper.clamp(MathHelper.subtractAngles(from[0], to[0]), -limit, limit);
        to[1] = from[1] + MathHelper.clamp(MathHelper.subtractAngles(from[1], to[1]), -limit, limit);
    }

}
