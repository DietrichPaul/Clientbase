package de.dietrichpaul.clientbase.features.rotation.strafe.impl;

import de.dietrichpaul.clientbase.event.StrafeInputEvent;
import de.dietrichpaul.clientbase.features.rotation.strafe.CorrectMovement;

public class SilentMoveFix extends CorrectMovement {

    @Override
    public void edit(float serverYaw, StrafeInputEvent event) {
        if (event.getMoveForward() != 0 || event.getMoveSideways() != 0) {
            double angle = mc.player.getYaw() + Math.toDegrees(Math.atan2(-event.getMoveSideways(), event.getMoveForward()));
            event.setMoveForward((int) Math.round(Math.cos(Math.toRadians(angle - serverYaw))));
            event.setMoveSideways((int) Math.round(-Math.sin(Math.toRadians(angle - serverYaw))));
        }
    }
}
