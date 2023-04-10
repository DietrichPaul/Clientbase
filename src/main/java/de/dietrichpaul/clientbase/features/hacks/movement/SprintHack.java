package de.dietrichpaul.clientbase.features.hacks.movement;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.dietrichpaul.clientbase.event.KeyPressedStateEvent;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.properties.impl.BooleanProperty;

public class SprintHack extends Hack {

    private final BooleanProperty allDirection = new BooleanProperty("AllDirection", false);

    public SprintHack() {
        super("Sprint", HackCategory.MOVEMENT);
        addProperty(allDirection);
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
