package de.dietrichpaul.clientbase.injection.mixin.rotation;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.rotation.RotationEngine;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Redirect(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;prevPitch:F", ordinal = 0))
    public float getServerPitch(LivingEntity instance) {
        if (instance instanceof ClientPlayerEntity) {
            RotationEngine engine = ClientBase.getInstance().getRotationEngine();
            if (engine.isRotating())
                return engine.getPrevPitch();
        }
        return instance.prevPitch;
    }

    @Redirect(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getPitch()F", ordinal = 0))
    public float getClientPitch(LivingEntity instance) {
        if (instance instanceof ClientPlayerEntity) {
            RotationEngine engine = ClientBase.getInstance().getRotationEngine();
            if (engine.isRotating())
                return engine.getPitch();
        }
        return instance.getPitch();
    }
}