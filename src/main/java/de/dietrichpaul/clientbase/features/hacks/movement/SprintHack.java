package de.dietrichpaul.clientbase.features.hacks.movement;

import de.dietrichpaul.clientbase.event.KeyPressedStateListener;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.properties.impl.BooleanProperty;
import de.dietrichpaul.clientbase.ClientBase;

public class SprintHack extends Hack implements KeyPressedStateListener {

    private final BooleanProperty allDirection = new BooleanProperty("AllDirection", false);

    public SprintHack() {
        super("Sprint", HackCategory.MOVEMENT);
        addProperty(allDirection);
    }

    @Override
    protected void onEnable() {
        ClientBase.getInstance().getEventDispatcher().subscribe(KeyPressedStateListener.class, this);
    }

    @Override
    protected void onDisable() {
        ClientBase.getInstance().getEventDispatcher().unsubscribe(KeyPressedStateListener.class, this);
    }

    @Override
    public void onKeyPressedState(KeyPressedStateEvent event) {
        if (event.keyBinding == mc.options.sprintKey) event.pressed = true;
    }
}
