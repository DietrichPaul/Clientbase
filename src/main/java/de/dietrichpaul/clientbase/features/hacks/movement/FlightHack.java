package de.dietrichpaul.clientbase.features.hacks.movement;

import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;

public class FlightHack extends Hack {
    private boolean wasFlyingAllowed = false;

    public FlightHack() {
        super("Flight", HackCategory.MOVEMENT);
    }

    protected @Override void onEnable() {
        this.wasFlyingAllowed = mc.player.getAbilities().allowFlying;
        mc.player.getAbilities().allowFlying = true;
    }

    protected @Override void onDisable() {
        mc.player.getAbilities().allowFlying = this.wasFlyingAllowed;
    }
}
