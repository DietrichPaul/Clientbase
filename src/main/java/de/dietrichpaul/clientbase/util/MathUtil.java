package de.dietrichpaul.clientbase.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathUtil {

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
