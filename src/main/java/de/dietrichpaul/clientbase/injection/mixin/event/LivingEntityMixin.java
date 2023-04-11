package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.JumpListener;
import de.dietrichpaul.clientbase.event.rotate.RotationGetListener;
import de.dietrichpaul.clientbase.features.rotation.RotationEngine;
import de.florianmichael.dietrichevents.EventDispatcher;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract float getYaw(float tickDelta);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F", ordinal = 1))
    public float replacedTrackedYaw1(LivingEntity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getYaw();
        }
        return EventDispatcher.g().post(new RotationGetListener.RotationGetEvent(getYaw(), getPitch())).yaw;
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F", ordinal = 0))
    public float replacedTrackedYaw0(LivingEntity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getYaw();
        }
        return EventDispatcher.g().post(new RotationGetListener.RotationGetEvent(getYaw(), getPitch())).yaw;
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getPitch()F", ordinal = 1))
    public float replacedTrackedPitch1(LivingEntity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getPitch();
        }
        return EventDispatcher.g().post(new RotationGetListener.RotationGetEvent(getYaw(), getPitch())).pitch;
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getPitch()F", ordinal = 0))
    public float replacedTrackedPitch0(LivingEntity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getPitch();
        }
        return EventDispatcher.g().post(new RotationGetListener.RotationGetEvent(getYaw(), getPitch())).pitch;
    }

    @Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    public float getJumpYaw(LivingEntity instance) {
        if (instance instanceof ClientPlayerEntity) {
            return EventDispatcher.g().post(new JumpListener.JumpEvent(instance.getYaw())).yaw;
        }
        return instance.getYaw();
    }

    @Redirect(method = "turnHead", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    public float getBodyYaw(LivingEntity instance) {
        if (instance instanceof ClientPlayerEntity) {
            RotationEngine engine = ClientBase.getInstance().getRotationEngine();
            if (engine.isRotating())
                return engine.getYaw();
        }
        return instance.getYaw();
    }
}
