package de.dietrichpaul.clientbase.injection.mixin.event;

import com.mojang.authlib.GameProfile;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.event.rotate.RotationSetListener;
import de.dietrichpaul.clientbase.event.rotate.SendRotationListener;
import de.florianmichael.dietrichevents.EventDispatcher;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow public abstract float getYaw(float tickDelta);

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
    public float onGetYaw(ClientPlayerEntity instance) {
        SendRotationListener.SendRotationEvent sendRotationEvent = EventDispatcher.g().post(new SendRotationListener.SendRotationEvent(instance.getYaw(), SendRotationListener.Type.YAW));
        return sendRotationEvent.value;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
    public float onGetPitch(ClientPlayerEntity instance) {
        SendRotationListener.SendRotationEvent sendRotationEvent = EventDispatcher.g().post(new SendRotationListener.SendRotationEvent(instance.getPitch(), SendRotationListener.Type.PITCH));
        return sendRotationEvent.value;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V", shift = At.Shift.BEFORE))
    public void onUpdate(CallbackInfo ci) {
        EventDispatcher.g().post(new UpdateListener.UpdateEvent());
    }

    @Override
    protected void setRotation(float yaw, float pitch) {
        super.setRotation(yaw, pitch);
        EventDispatcher.g().post(new RotationSetListener.RotationSetEvent(getYaw(), getPitch(), true, true));
    }
}
