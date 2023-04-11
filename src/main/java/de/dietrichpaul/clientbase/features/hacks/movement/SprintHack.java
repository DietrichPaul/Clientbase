package de.dietrichpaul.clientbase.features.hacks.movement;

import de.dietrichpaul.clientbase.event.KeyPressedStateListener;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.properties.impl.BooleanProperty;
import de.florianmichael.dietrichevents.EventDispatcher;

public class SprintHack extends Hack implements KeyPressedStateListener {

    private final BooleanProperty allDirection = new BooleanProperty("AllDirection", false);

    public SprintHack() {
        super("Sprint", HackCategory.MOVEMENT);
        addProperty(allDirection);
    }

    @Override
    protected void onEnable() {
        EventDispatcher.g().subscribe(KeyPressedStateListener.class, this);
    }

    @Override
    protected void onDisable() {
        EventDispatcher.g().unsubscribe(KeyPressedStateListener.class, this);
    }

    @Override
    public void onKeyPressedState(KeyPressedStateEvent event) {
        if (event.keyBinding == mc.options.sprintKey) event.pressed = true;
    }
}
