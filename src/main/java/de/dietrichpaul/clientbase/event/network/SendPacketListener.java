package de.dietrichpaul.clientbase.event.network;

import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import de.florianmichael.dietrichevents.types.CancellableEvent;
import net.minecraft.network.packet.Packet;

public interface SendPacketListener extends Listener {

    void onSendPacket(SendPacketEvent event);

    class SendPacketEvent extends CancellableEvent<SendPacketListener> {

        private Packet<?> packet;
        private final EventExecutor<SendPacketListener> executor = listener -> listener.onSendPacket(this);

        public SendPacketEvent(Packet<?> packet) {
            this.packet = packet;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        public void setPacket(Packet<?> packet) {
            this.packet = packet;
        }

        @Override
        public EventExecutor<SendPacketListener> getEventExecutor() {
            return executor;
        }

        @Override
        public Class<SendPacketListener> getListenerType() {
            return SendPacketListener.class;
        }
    }

}
