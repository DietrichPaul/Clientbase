package de.dietrichpaul.clientbase.features.rotation.strafe.impl;

import de.dietrichpaul.clientbase.event.StrafeInputEvent;
import de.dietrichpaul.clientbase.features.rotation.strafe.CorrectMovement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BreezilyFix extends CorrectMovement {

    @Override
    public void edit(float serverYaw, StrafeInputEvent event) {
        if (event.getMoveSideways() == 0 && event.getMoveForward() == 0)
            return;

        double angle = mc.player.getYaw() + Math.toDegrees(Math.atan2(-event.getMoveSideways(), event.getMoveForward()));

        // cos(a): a=0 -> 1, a=45 -> 0.5, a=90 -> 0
        // cos(0) = 1; 1² = 1
        // cos(45) = 0.707...; 0.707² = 0.5
        // cos(90) = 0; 0² = 0
        double impactX = Math.cos(Math.toRadians(angle)) * Math.cos(Math.toRadians(angle));
        double impactZ = Math.sin(Math.toRadians(angle)) * Math.sin(Math.toRadians(angle));

        Vec3d next = mc.player.getPos().add(-Math.sin(Math.toRadians(angle)) * 0.5, 0, Math.cos(Math.toRadians(angle)) * 0.5);
        Vec3d center = Vec3d.ofCenter(BlockPos.ofFloored(next));
        Vec3d goTo = new Vec3d(
                MathHelper.lerp(impactX, next.x, center.x),
                0,
                MathHelper.lerp(impactZ, next.z, center.z)
        );

        double sollAngle = Math.toDegrees(Math.atan2(mc.player.getX() - goTo.x, goTo.z - mc.player.getZ()));
        event.setMoveForward((int) Math.round(Math.cos(Math.toRadians(sollAngle - serverYaw))));
        event.setMoveSideways((int) Math.round(-Math.sin(Math.toRadians(sollAngle - serverYaw))));
    }

}
