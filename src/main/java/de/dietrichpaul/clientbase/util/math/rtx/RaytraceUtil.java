package de.dietrichpaul.clientbase.util.math.rtx;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;

public class RaytraceUtil {

    public static Raytrace raytrace(MinecraftClient client, Entity entity, float[] rotations, float[] prevRotations,
                                    float range, float tickDelta) {
        if (entity == null || client.world == null)
            return null;

        Entity targetedEntity = null;
        HitResult crosshairTarget = null;
        double blockReach = (double) Math.max(client.interactionManager.getReachDistance(), range);
        crosshairTarget = raycastBlocks(entity, blockReach, rotations, prevRotations, tickDelta, false);
        Vec3d camera = entity.getCameraPosVec(tickDelta);
        boolean survivalRange = false;
        double squaredReach = blockReach;
        if (client.interactionManager.hasExtendedReach()) {
            squaredReach = 6.0;
            blockReach = squaredReach;
        } else {
            //if (blockReach > 3.0) { immer true
            survivalRange = true;
            //}
        }
        squaredReach *= squaredReach;
        if (crosshairTarget != null) {
            squaredReach = crosshairTarget.getPos().squaredDistanceTo(camera);
        }
        Vec3d rotationVec = getRotationVec(rotations, prevRotations, 1.0F);
        Vec3d ray = camera.add(rotationVec.x * blockReach, rotationVec.y * blockReach, rotationVec.z * blockReach);
        Box box = entity.getBoundingBox().stretch(rotationVec.multiply(blockReach)).expand(1.0, 1.0, 1.0);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(entity, camera, ray, box, (entityx) -> {
            return !entityx.isSpectator() && entityx.canHit();
        }, squaredReach);
        if (entityHitResult != null) {
            Entity entity2 = entityHitResult.getEntity();
            Vec3d vec3d4 = entityHitResult.getPos();
            double g = camera.squaredDistanceTo(vec3d4);
            if (survivalRange && g > range * range) {
                crosshairTarget = BlockHitResult.createMissed(vec3d4, Direction.getFacing(rotationVec.x, rotationVec.y, rotationVec.z), BlockPos.ofFloored(vec3d4));
            } else if (g < squaredReach || crosshairTarget == null) {
                crosshairTarget = entityHitResult;
                if (entity2 instanceof LivingEntity || entity2 instanceof ItemFrameEntity) {
                    targetedEntity = entity2;
                }
            }
        }

        return new Raytrace(targetedEntity, crosshairTarget);
    }
    public static HitResult raycastBlocks(Entity entity, double maxDistance,float[] rotations, float[] prevRotations, float tickDelta, boolean includeFluids) {
        Vec3d vec3d = entity.getCameraPosVec(tickDelta);
        Vec3d vec3d2 = getRotationVec(rotations, prevRotations, tickDelta);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance);
        return entity.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, entity));
    }

    public static float getPitch(float[] rotations, float[] prevRotations, float tickDelta) {
        return tickDelta == 1.0F ? rotations[1] : MathHelper.lerp(tickDelta, prevRotations[1], rotations[1]);
    }

    public static float getYaw(float[] rotations, float[] prevRotations, float tickDelta) {
        return tickDelta == 1.0F ? rotations[0] : MathHelper.lerp(tickDelta, prevRotations[0], rotations[0]);
    }

    public static Vec3d getRotationVec(float[] rotations, float[] prevRotations, float tickDelta) {
        return getRotationVector(getPitch(rotations, prevRotations, tickDelta), getYaw(rotations, prevRotations, tickDelta));
    }

    public static Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d((double)(i * j), (double)(-k), (double)(h * j));
    }
}
