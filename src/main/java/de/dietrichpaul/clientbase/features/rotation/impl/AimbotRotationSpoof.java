package de.dietrichpaul.clientbase.features.rotation.impl;

import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.rotation.RotationSpoof;
import de.dietrichpaul.clientbase.properties.PropertyGroup;
import de.dietrichpaul.clientbase.properties.impl.FloatProperty;
import de.dietrichpaul.clientbase.properties.impl.IntProperty;
import de.dietrichpaul.clientbase.properties.impl.TargetProperty;
import de.dietrichpaul.clientbase.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AimbotRotationSpoof extends RotationSpoof {

    private final TargetProperty targetProperty = new TargetProperty("Targets", true, EntityType.PLAYER);
    private final IntProperty maxTargets = new IntProperty("Max targets", 1, 1, 20);
    private final FloatProperty aimRangeProperty = new FloatProperty("AimRange", 1.5F, 0, 6);
    private final FloatProperty rangeProperty = new FloatProperty("Range", 3, 0, 6);
    private final IntProperty fovProperty = new IntProperty("FOV", 360, 0, 360);

    private final Hack parent;

    private final List<Entity> targets = new LinkedList<>();

    public AimbotRotationSpoof(Hack parent, PropertyGroup propertyGroup) {
        super(propertyGroup);
        this.parent = parent;
        PropertyGroup targetGroup = parent.addPropertyGroup("Targets");
        targetGroup.addProperty(targetProperty);
        targetGroup.addProperty(maxTargets);
        targetGroup.addProperty(aimRangeProperty);
        targetGroup.addProperty(rangeProperty);
        targetGroup.addProperty(fovProperty);
    }

    @Override
    public boolean pickTarget() {
        targets.clear();
        Vec3d camera = mc.player.getCameraPosVec(1.0F);
        for (Iterator<Entity> iterator = mc.world.getEntities().iterator(); iterator.hasNext() && targets.size() < maxTargets.getValue(); ) {
            Entity entity = iterator.next();
            if (!targetProperty.filter(entity)) // entity ist kein target
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
        return !targets.isEmpty();
    }

    @Override
    public void rotate(float[] rotations, boolean tick, float partialTicks) {
        Entity primaryTarget = targets.get(0);
        Vec3d hitVec = primaryTarget.getCameraPosVec(partialTicks);
        Vec3d camera = mc.player.getCameraPosVec(partialTicks);
        MathUtil.getRotations(camera, hitVec, rotations);
    }

    @Override
    public boolean isToggled() {
        return parent.isToggled();
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
