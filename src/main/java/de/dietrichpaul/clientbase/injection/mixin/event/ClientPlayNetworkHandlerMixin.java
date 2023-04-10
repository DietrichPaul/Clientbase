package de.dietrichpaul.clientbase.injection.mixin.event;

import com.darkmagician6.eventapi.EventManager;
import de.dietrichpaul.clientbase.event.rotate.RotateSetEvent;
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
        EventManager.call(new RotateSetEvent(-180, 0, true, false));
    }

}
