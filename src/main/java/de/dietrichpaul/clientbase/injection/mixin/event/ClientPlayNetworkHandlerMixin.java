package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.event.rotate.RotationSetListener;
import de.florianmichael.dietrichevents.EventDispatcher;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onGameJoin", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;setYaw(F)V"))
    public void onFlipPlayer(GameJoinS2CPacket packet, CallbackInfo ci) {
        EventDispatcher.g().post(new RotationSetListener.RotationSetEvent(-180, 0, true, false));
    }
}
