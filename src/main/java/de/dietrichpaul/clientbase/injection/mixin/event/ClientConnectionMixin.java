package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.network.ReceivePacketListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {

    @Shadow
    private static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener) {
    }

    @Redirect(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V"))
    public void onHandlePacket(Packet<?> packet, PacketListener listener) {
        ReceivePacketListener.ReceivePacketEvent event = new ReceivePacketListener.ReceivePacketEvent(packet, listener);
        ClientBase.INSTANCE.getEventDispatcher().post(event);
        if (event.isCancelled())
            return;
        handlePacket(event.getPacket(), event.getListener());
    }

}
