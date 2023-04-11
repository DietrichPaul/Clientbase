package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

public interface PreTickRaytraceListener extends Listener {
    void onPreTickRaytrace();

    class PreTickRaytraceEvent extends AbstractEvent<PreTickRaytraceListener> {
        private final EventExecutor<PreTickRaytraceListener> eventExecutor = PreTickRaytraceListener::onPreTickRaytrace;

        @Override
        public EventExecutor<PreTickRaytraceListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<PreTickRaytraceListener> getListenerType() {
            return PreTickRaytraceListener.class;
        }
    }
}
