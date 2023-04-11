package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

public interface StrafeListener extends Listener {
    void onStrafe(final StrafeEvent event);

    class StrafeEvent extends AbstractEvent<StrafeListener> {
        private final EventExecutor<StrafeListener> eventExecutor = listener -> listener.onStrafe(this);
        public float yaw;

        public StrafeEvent(final float yaw) {
            this.yaw = yaw;
        }

        @Override
        public EventExecutor<StrafeListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<StrafeListener> getListenerType() {
            return StrafeListener.class;
        }
    }
}
