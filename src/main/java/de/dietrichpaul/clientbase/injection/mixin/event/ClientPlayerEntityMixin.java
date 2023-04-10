package de.dietrichpaul.clientbase.injection.mixin.event;

import com.darkmagician6.eventapi.EventManager;
import com.mojang.authlib.GameProfile;
import de.dietrichpaul.clientbase.event.rotate.RotateSetEvent;
import de.dietrichpaul.clientbase.event.UpdateEvent;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V", shift = At.Shift.BEFORE))
    public void onUpdate(CallbackInfo ci) {
        EventManager.call(UpdateEvent.INSTANCE);
    }

    @Override
    protected void setRotation(float yaw, float pitch) {
        super.setRotation(yaw, pitch);
        EventManager.call(new RotateSetEvent(getYaw(), getPitch(), true, true));
    }

}
