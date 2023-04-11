package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import net.minecraft.client.option.KeyBinding;

public interface KeyPressedStateListener extends Listener {
    void onKeyPressedState(final KeyPressedStateEvent keyPressedStateEvent);

    class KeyPressedStateEvent extends AbstractEvent<KeyPressedStateListener> {
        private final EventExecutor<KeyPressedStateListener> eventExecutor = listener -> listener.onKeyPressedState(this);
        public KeyBinding keyBinding;
        public boolean pressed;

        public KeyPressedStateEvent(final KeyBinding keyBinding, final boolean pressed) {
            this.keyBinding = keyBinding;
            this.pressed = pressed;
        }

        @Override
        public EventExecutor<KeyPressedStateListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<KeyPressedStateListener> getListenerType() {
            return KeyPressedStateListener.class;
        }
    }
}
