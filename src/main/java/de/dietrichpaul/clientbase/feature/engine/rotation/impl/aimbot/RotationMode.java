package de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot;

import de.dietrichpaul.clientbase.util.math.MathUtil;
import de.dietrichpaul.clientbase.util.math.ProjectedBox;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.SimplexNoise;

import static de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot.RotationMethod.mc;

public enum RotationMode {

    HEAD("Head", (camera, box, target, rotations, prevRotations, partialTicks) -> {
        MathUtil.getRotations(camera, target.getCameraPosVec(partialTicks), rotations);
    }),
    CENTER("Center", ((camera, aabb, target, rotations, prevRotations, partialTicks) -> {
        MathUtil.getRotations(camera, aabb.getCenter(), rotations);
    })),
    CLOSEST("Closest", (camera, aabb, target, rotations, prevRotations, partialTicks) -> {
        MathUtil.getRotations(camera, MathUtil.clamp(camera, aabb), rotations);
    }),
    NEAREST("Nearest", (camera, aabb, target, rotations, prevRotations, partialTicks) -> {
        float[] targetRotations = new float[2];
        Box box = target.getBoundingBox();
        Vec3d fixCamera = mc.player.getCameraPosVec(1.0F);
        Vec3d polar = Vec3d.fromPolar(prevRotations[1], prevRotations[0]);
        double rayLength = camera.distanceTo(box.getCenter()) * 0.5 + camera.distanceTo(MathUtil.clamp(camera, box)) * 0.5;
        MathUtil.getRotations(fixCamera, MathUtil.clamp(fixCamera.add(polar.multiply(rayLength)), box), targetRotations);
        rotations[0] = MathHelper.lerpAngleDegrees(partialTicks, prevRotations[0], targetRotations[0]);
        rotations[1] = MathHelper.lerpAngleDegrees(partialTicks, prevRotations[1], targetRotations[1]);
    }),
    SIMPLEX("Simplex", new RotationMethod() {

        private float x = 0.5F;
        private float prevX;

        private float state;

        @Override
        public void tick(Entity target) {

            Vec3d targetVelocity = new Vec3d(target.getX() - target.prevX, target.getY() - target.prevY,
                    target.getZ() - target.prevZ);
            Vec3d velocity = new Vec3d(mc.player.getX() - mc.player.prevX, mc.player.getY() - mc.player.prevY,
                    mc.player.getZ() - mc.player.prevZ);
            double velocityLength = targetVelocity.length() + velocity.length();
            prevX = x;
            x = 0.5F + SimplexNoise.noise(state, 0) / 2;
            if (velocityLength > 0.07) {
                state += 0.05F;
            }
        }

        @Override
        public void getRotations(Vec3d camera, Box aabb, Entity target, float[] rotations, float[] prevRotations, float partialTicks) {
            if (aabb.contains(camera)) {
                float[] nearest = new float[2];
                Box box = target.getBoundingBox();
                Vec3d fixCamera = mc.player.getCameraPosVec(1.0F);
                Vec3d polar = Vec3d.fromPolar(prevRotations[1], prevRotations[0]);
                double rayLength = camera.distanceTo(box.getCenter()) * 0.5 + camera.distanceTo(MathUtil.clamp(camera, box)) * 0.5;
                MathUtil.getRotations(fixCamera, MathUtil.clamp(fixCamera.add(polar.multiply(rayLength)), box), nearest);
                rotations[0] = MathHelper.lerpAngleDegrees(partialTicks, prevRotations[0], nearest[0]);
                rotations[1] = MathHelper.lerpAngleDegrees(partialTicks, prevRotations[1], nearest[1]);
                return;
            }

            ProjectedBox projBox = new ProjectedBox(camera, aabb);
            Vec3d bestVec = MathUtil.clamp(camera, aabb);

            float[] bestRotations = new float[2];
            MathUtil.getRotations(camera, bestVec, bestRotations);

            rotations[0] = projBox.getYaw(MathHelper.lerp(partialTicks, prevX, x));
            rotations[1] = projBox.getPitch(0.5F);


            float[] nearest = new float[2];
            Box box = target.getBoundingBox();
            Vec3d fixCamera = mc.player.getCameraPosVec(1.0F);
            Vec3d polar = Vec3d.fromPolar(prevRotations[1], prevRotations[0]);
            double rayLength = camera.distanceTo(box.getCenter()) * 0.5 + camera.distanceTo(MathUtil.clamp(camera, box)) * 0.5;
            MathUtil.getRotations(fixCamera, MathUtil.clamp(fixCamera.add(polar.multiply(rayLength)), box), nearest);
            rotations[1] = MathHelper.lerpAngleDegrees(partialTicks, prevRotations[1], nearest[1]);
        }
    });

    private String name;
    private RotationMethod method;

    RotationMode(String name, RotationMethod method) {
        this.name = name;
        this.method = method;
    }

    @Override
    public String toString() {
        return name;
    }

    public RotationMethod getMethod() {
        return method;
    }
}
