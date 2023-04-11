package de.dietrichpaul.clientbase.event.rotate;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

public interface SendRotationListener extends Listener {
    void onSendYaw(final SendRotationEvent event);
    void onSendPitch(final SendRotationEvent event);

    class SendRotationEvent extends AbstractEvent<SendRotationListener> {
        private final EventExecutor<SendRotationListener> eventExecutor;
        public float value;

        public SendRotationEvent(final float value, final Type type) {
            if (type == Type.YAW) {
                this.eventExecutor = listener -> listener.onSendYaw(this);
            } else {
                this.eventExecutor = listener -> listener.onSendPitch(this);
            }
            this.value = value;
        }

        @Override
        public EventExecutor<SendRotationListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<SendRotationListener> getListenerType() {
            return SendRotationListener.class;
        }
    }

    enum Type {
        YAW, PITCH
    }
}
