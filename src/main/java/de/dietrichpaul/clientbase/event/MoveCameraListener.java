package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

public interface MoveCameraListener extends Listener {
    void onMoveCamera(final float tickDelta);

    class MoveCameraEvent extends AbstractEvent<MoveCameraListener> {
        private final EventExecutor<MoveCameraListener> eventExecutor;

        public MoveCameraEvent(final float tickDelta) {
            this.eventExecutor = listener -> listener.onMoveCamera(tickDelta);
        }

        @Override
        public EventExecutor<MoveCameraListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<MoveCameraListener> getListenerType() {
            return MoveCameraListener.class;
        }
    }
}
