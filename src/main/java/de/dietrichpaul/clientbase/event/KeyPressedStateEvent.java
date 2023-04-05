package de.dietrichpaul.clientbase.event;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.option.KeyBinding;

public class KeyPressedStateEvent implements Event {

    private final KeyBinding keyBinding;
    private boolean pressed;

    public KeyPressedStateEvent(KeyBinding keyBinding, boolean pressed) {
        this.keyBinding = keyBinding;
        this.pressed = pressed;
    }

    public KeyBinding getKeyBinding() {
        return keyBinding;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }
}
