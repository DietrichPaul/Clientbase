package de.dietrichpaul.clientbase.features.hacks.movement;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.properties.impl.BooleanProperty;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class FlightHack extends Hack implements UpdateListener {
    private final BooleanProperty onGroundSpoof = new BooleanProperty("OnGround", true);
    private boolean wasFlyingAllowed = false;

    public FlightHack() {
        super("Flight", HackCategory.MOVEMENT);
        this.addProperty(this.onGroundSpoof);
    }

    protected @Override void onEnable() {
        ClientBase.getInstance().getEventDispatcher().subscribe(UpdateListener.class, this);
        this.wasFlyingAllowed = mc.player.getAbilities().allowFlying;
        mc.player.getAbilities().allowFlying = true;
    }

    protected @Override void onDisable() {
        ClientBase.getInstance().getEventDispatcher().unsubscribe(UpdateListener.class, this);
        mc.player.getAbilities().allowFlying = this.wasFlyingAllowed;
    }

    public @Override void onUpdate() {
        if (!onGroundSpoof.getState() || mc.player.fallDistance < 0.3F) return;
        mc.player.fallDistance = 0.0F;
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }
}
