package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

public interface UpdateListener extends Listener {
    void onUpdate();

    class UpdateEvent extends AbstractEvent<UpdateListener> {
        private final EventExecutor<UpdateListener> eventExecutor = UpdateListener::onUpdate;

        @Override
        public EventExecutor<UpdateListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<UpdateListener> getListenerType() {
            return UpdateListener.class;
        }
    }
}
