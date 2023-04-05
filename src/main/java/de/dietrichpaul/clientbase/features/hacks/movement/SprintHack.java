package de.dietrichpaul.clientbase.features.hacks.movement;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.dietrichpaul.clientbase.event.KeyPressedStateEvent;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;

public class SprintHack extends Hack {

    public SprintHack() {
        super("Sprint", HackCategory.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        EventManager.register(this);
    }

    @Override
    protected void onDisable() {
        EventManager.unregister(this);
    }

    @EventTarget
    public void onKeyPressedState(KeyPressedStateEvent event) {
        if (event.getKeyBinding() == mc.options.sprintKey)
            event.setPressed(true);
    }
}
