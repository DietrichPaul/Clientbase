package de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot;

import de.dietrichpaul.clientbase.util.math.MathUtil;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

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
