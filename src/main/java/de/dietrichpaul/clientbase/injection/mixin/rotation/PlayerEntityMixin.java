package de.dietrichpaul.clientbase.injection.mixin.rotation;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.rotation.RotationEngine;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "tickNewAi", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getYaw()F"))
    public float getYaw(PlayerEntity instance) {
        if (instance instanceof ClientPlayerEntity) {
            RotationEngine engine = ClientBase.getInstance().getRotationEngine();
            if (engine.isRotating())
                return engine.getYaw();
        }
        return instance.getYaw();
    }

}