package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import de.florianmichael.dietrichevents.types.CancellableEvent;

public interface RaytraceListener extends Listener {
    void onRaytrace(final RaytraceEvent event);

    class RaytraceEvent extends CancellableEvent<RaytraceListener> {
        private final EventExecutor<RaytraceListener> eventExecutor = listener -> listener.onRaytrace(this);
        public float tickDelta;

        public RaytraceEvent(final float tickDelta) {
            this.tickDelta = tickDelta;
        }

        @Override
        public EventExecutor<RaytraceListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<RaytraceListener> getListenerType() {
            return RaytraceListener.class;
        }
    }
}
