/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.engine.rotation.impl;

import de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot.RotationMode;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.engine.rotation.RotationSpoof;
import de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot.Priority;
import de.dietrichpaul.clientbase.property.PropertyGroup;
import de.dietrichpaul.clientbase.property.impl.EntityTypeProperty;
import de.dietrichpaul.clientbase.property.impl.EnumProperty;
import de.dietrichpaul.clientbase.property.impl.FloatProperty;
import de.dietrichpaul.clientbase.property.impl.IntProperty;
import de.dietrichpaul.clientbase.util.math.MathUtil;
import de.dietrichpaul.clientbase.util.minecraft.rtx.Raytrace;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AimbotRotationSpoof extends RotationSpoof {

    private final EntityTypeProperty entityTypeProperty = new EntityTypeProperty("Entity Type", true, EntityType.PLAYER);
    private final IntProperty maxTargets = new IntProperty("Max targets", 1, 1, 20);
    private final FloatProperty aimRangeProperty = new FloatProperty("AimRange", 1.5F, 0, 6);
    private final FloatProperty rangeProperty = new FloatProperty("Range", 3, 0, 6);
    private final IntProperty fovProperty = new IntProperty("FOV", 360, 0, 360);
    private final EnumProperty<Priority> priorityProperty = new EnumProperty<>("Priority", Priority.DISTANCE, Priority.values(), Priority.class);
    private final EnumProperty<RotationMode> rotationModeProperty = new EnumProperty<>("Rotation", RotationMode.CLOSEST, RotationMode.values(), RotationMode.class);
    private final Hack parent;

    private final List<Entity> targets = new LinkedList<>();

    public AimbotRotationSpoof(Hack parent, PropertyGroup propertyGroup) {
        super(propertyGroup);
        this.parent = parent;
        PropertyGroup targetGroup = parent.addPropertyGroup("Targets");
        targetGroup.addProperty(entityTypeProperty);
        targetGroup.addProperty(maxTargets);
        targetGroup.addProperty(aimRangeProperty);
        targetGroup.addProperty(rangeProperty);
        targetGroup.addProperty(fovProperty);
        targetGroup.addProperty(priorityProperty);

        propertyGroup.addProperty(rotationModeProperty);
    }

    @Override
    public void tick() {
        rotationModeProperty.getValue().getMethod().tick(targets.get(0));
    }

    @Override
    public boolean pickTarget() {
        targets.clear();
        Vec3d camera = mc.player.getCameraPosVec(1.0F);
        for (Entity entity : mc.world.getEntities()) {
            if (entity == null || entity instanceof ClientPlayerEntity)
                continue;

            if (!entityTypeProperty.filter(entity)) // entity ist kein target
                continue;

            // entity außer distanz
            Vec3d closest = MathUtil.clamp(camera, entity.getBoundingBox().expand(entity.getTargetingMargin()));
            if (camera.distanceTo(closest) > aimRangeProperty.getValue() + rangeProperty.getValue()) {
                continue;
            }

            // entity ausm fov
            if (fovProperty.getValue() < 360) {
                double angleDiff = MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(entity.getZ() - camera.z, entity.getX() - camera.x)) - 90 - mc.player.getYaw());
                if (Math.abs(angleDiff) > fovProperty.getValue() / 2.0)
                    continue;
            }

            targets.add(entity);
        }
        targets.sort(priorityProperty.getValue().getComparator());
        while (targets.size() > maxTargets.getValue()) {
            targets.remove(targets.size() - 1);
        }
        return !targets.isEmpty();
    }
    @Override
    public void rotate(float[] rotations, float[] prevRotations, boolean tick, float partialTicks) {
        Vec3d camera = mc.player.getCameraPosVec(partialTicks);
        Entity primaryTarget = targets.get(0);
        Box aabb = primaryTarget.getBoundingBox()
                .offset(-primaryTarget.getX(), -primaryTarget.getY(), -primaryTarget.getZ())
                .offset(primaryTarget.prevX, primaryTarget.prevY, primaryTarget.prevZ)
                .offset(new Vec3d(
                                primaryTarget.getX() - primaryTarget.prevX,
                                primaryTarget.getY() - primaryTarget.prevY,
                                primaryTarget.getZ() - primaryTarget.prevZ
                        ).multiply(partialTicks)
                );
        rotationModeProperty.getValue().getMethod().getRotations(camera, aabb, primaryTarget, rotations, prevRotations, partialTicks);
    }

    @Override
    public boolean isToggled() {
        return parent.isToggled();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public float getRange() {
        return rangeProperty.getValue();
    }

    @Override
    public Raytrace getTarget() {
        Raytrace rtx = new Raytrace(targets.get(0), new EntityHitResult(targets.get(0), targets.get(0).getBoundingBox().getCenter()));
        if (targets.get(0).distanceTo(mc.player) > getRange()) {
            rtx = new Raytrace(null, BlockHitResult.createMissed(mc.player.getCameraPosVec(1.0F), Direction.UP, BlockPos.ORIGIN));
        }
        return rtx;
    }

    public Entity getPrimaryTarget() {
        return targets.get(0);
    }

    public List<Entity> getTargets() {
        return targets;
    }

    public boolean hasTarget() {
        return !targets.isEmpty();
    }
}
