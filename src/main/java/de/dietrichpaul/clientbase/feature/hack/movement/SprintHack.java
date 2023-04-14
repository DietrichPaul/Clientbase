package de.dietrichpaul.clientbase.feature.hack.movement;

import de.dietrichpaul.clientbase.event.KeyPressedStateListener;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.property.impl.BooleanProperty;
import de.dietrichpaul.clientbase.ClientBase;

public class SprintHack extends Hack implements KeyPressedStateListener {

    private final BooleanProperty allDirection = new BooleanProperty("AllDirection", false);

    public SprintHack() {
        super("Sprint", HackCategory.MOVEMENT);
        addProperty(allDirection);
    }

    @Override
    protected void onEnable() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(KeyPressedStateListener.class, this);
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribe(KeyPressedStateListener.class, this);
    }

    @Override
    public void onKeyPressedState(KeyPressedStateEvent event) {
        if (event.keyBinding == mc.options.sprintKey) event.pressed = true;
    }
}
