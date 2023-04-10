package de.dietrichpaul.clientbase.event;

import com.darkmagician6.eventapi.events.Event;

public class KeyEvent implements Event {

    private final int key;
    private final int scan;
    private final int action;
    private final int modifiers;

    public KeyEvent(int key, int scan, int action, int modifiers) {
        this.key = key;
        this.scan = scan;
        this.action = action;
        this.modifiers = modifiers;
    }

    public int getKey() {
        return key;
    }

    public int getScan() {
        return scan;
    }

    public int getAction() {
        return action;
    }

    public int getModifiers() {
        return modifiers;
    }
}
