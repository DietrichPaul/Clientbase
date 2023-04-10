package de.dietrichpaul.clientbase.util;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathUtil {

    public static Vec3d clamp(Vec3d vec, Box box) {
        return new Vec3d(
                MathHelper.clamp(vec.x, box.minX, box.maxX),
                MathHelper.clamp(vec.y, box.minY, box.maxY),
                MathHelper.clamp(vec.z, box.minZ, box.maxZ)
        );
    }

    public static void getRotations(Vec3d from, Vec3d to, float[] rotations) {
        Vec3d delta = from.subtract(to);
        rotations[0] = MathHelper.wrapDegrees((float) Math.toDegrees(Math.atan2(delta.x, -delta.z)));
        rotations[1] = (float) Math.toDegrees(Math.atan2(delta.y, delta.horizontalLength()));
        rotations[1] = MathHelper.wrapDegrees(rotations[1]);
        if (rotations[1] > 90) {
            rotations[0] += 180;
            rotations[1] = 180 - rotations[1];
        } else if (rotations[1] < -90) {
            rotations[0] += 180;
            rotations[1] = 180 + rotations[1];
        }
    }

}
