package de.dietrichpaul.clientbase.feature.engine.rotation.impl;

import de.dietrichpaul.clientbase.feature.engine.rotation.RotationSpoof;
import de.dietrichpaul.clientbase.feature.hack.world.ScaffoldWalkHack;
import de.dietrichpaul.clientbase.util.math.MathUtil;
import de.dietrichpaul.clientbase.util.minecraft.BlockUtil;
import de.dietrichpaul.clientbase.util.minecraft.rtx.Raytrace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class ScaffoldWalkRotationSpoof extends RotationSpoof {

    private ScaffoldWalkHack parent;

    private BlockPos blockPos;
    private Direction face;

    private boolean hasTarget;

    public ScaffoldWalkRotationSpoof(ScaffoldWalkHack hack) {
        super(hack);
        parent = hack;
    }

    @Override
    public boolean pickTarget() {
        hasTarget = false;

        BlockPos below = BlockPos.ofFloored(mc.player.getPos()).down();
        if (!BlockUtil.getBlockState(below).isAir()) return false;

        blockPos = null;
        face = null;

        Vec3d camera = mc.player.getCameraPosVec(1.0F);
        for (Direction direction : Direction.values()) {
            BlockPos offset = below.offset(direction);
            if (BlockUtil.getBlockState(offset).isAir()) continue;

            if (camera.squaredDistanceTo(Vec3d.ofCenter(below)) >= camera.squaredDistanceTo(Vec3d.of(offset)))
                continue;

            blockPos = offset;
            face = direction.getOpposite();
        }

        return (hasTarget = blockPos != null);
    }

    @Override
    public void rotate(float[] rotations, float[] prevRotations, boolean tick, float partialTicks) {
        Vec3d camera = mc.player.getCameraPosVec(1.0F);
        Vec3d hitVec = Vec3d.ofCenter(blockPos).add(Vec3d.of(face.getVector()).multiply(0.5));
        MathUtil.getRotations(camera, hitVec, rotations);
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    @Override
    public boolean isToggled() {
        return parent.isToggled();
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Raytrace getTarget() {
        return null;
    }
}
