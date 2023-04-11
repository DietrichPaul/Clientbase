package de.dietrichpaul.clientbase.features.rotation.strafe.impl;

import de.dietrichpaul.clientbase.event.StrafeInputEvent;
import de.dietrichpaul.clientbase.features.rotation.strafe.CorrectMovement;
import net.minecraft.util.math.MathHelper;

public class ErrorCorrectedFix extends CorrectMovement {

    private final double epsilon;
    private double partialAngles;

    public ErrorCorrectedFix(double epsilon) {
        this.epsilon = epsilon;
    }

    public void reset() {
        partialAngles = 0;
    }

    @Override
    public void edit(float serverYaw, StrafeInputEvent event) {
        if (event.getMoveForward() != 0 || event.getMoveSideways() != 0) {
            double angle = mc.player.getYaw() + Math.toDegrees(Math.atan2(-event.getMoveSideways(), event.getMoveForward()));
            double forward = Math.cos(Math.toRadians(partialAngles + angle - serverYaw));
            double sidewars = -Math.sin(Math.toRadians(partialAngles + angle - serverYaw));
            event.setMoveForward((int) Math.round(forward));
            event.setMoveSideways((int) Math.round(sidewars));
            double serverAngle = serverYaw + Math.toDegrees(Math.atan2(-event.getMoveSideways(), event.getMoveForward()));

            partialAngles += MathHelper.wrapDegrees(angle - serverAngle);
        }
    }
}
