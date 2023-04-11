package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

public interface JumpListener extends Listener {
    void onJump(final JumpEvent event);

    class JumpEvent extends AbstractEvent<JumpListener> {
        private final EventExecutor<JumpListener> eventExecutor = listener -> listener.onJump(this);
        public float yaw;

        public JumpEvent(final float yaw) {
            this.yaw = yaw;
        }

        @Override
        public EventExecutor<JumpListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<JumpListener> getListenerType() {
            return JumpListener.class;
        }
    }
}
