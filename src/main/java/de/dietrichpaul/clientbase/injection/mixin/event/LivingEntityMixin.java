package de.dietrichpaul.clientbase.injection.mixin.event;

import com.darkmagician6.eventapi.EventManager;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.JumpEvent;
import de.dietrichpaul.clientbase.event.rotate.RotateGetEvent;
import de.dietrichpaul.clientbase.features.rotation.RotationEngine;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F", ordinal = 1))
    public float replacedTrackedYaw1(LivingEntity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getYaw();
        }
        RotateGetEvent event = new RotateGetEvent(getYaw(), getPitch());
        EventManager.call(event);
        return event.getYaw();
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F", ordinal = 0))
    public float replacedTrackedYaw0(LivingEntity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getYaw();
        }
        RotateGetEvent event = new RotateGetEvent(getYaw(), getPitch());
        EventManager.call(event);
        return event.getYaw();
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getPitch()F", ordinal = 1))
    public float replacedTrackedPitch1(LivingEntity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getPitch();
        }
        RotateGetEvent event = new RotateGetEvent(getYaw(), getPitch());
        EventManager.call(event);
        return event.getPitch();
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getPitch()F", ordinal = 0))
    public float replacedTrackedPitch0(LivingEntity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getPitch();
        }
        RotateGetEvent event = new RotateGetEvent(getYaw(), getPitch());
        EventManager.call(event);
        return event.getPitch();
    }

    @Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    public float getJumpYaw(LivingEntity instance) {
        if (instance instanceof ClientPlayerEntity) {
            JumpEvent event = new JumpEvent(instance.getYaw());
            EventManager.call(event);
            return event.getYaw();
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