/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.hack.movement;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.property.impl.BooleanProperty;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Objects;

@SuppressWarnings("DataFlowIssue")
public class FlightHack extends Hack implements UpdateListener {
    private final BooleanProperty onGroundSpoof = new BooleanProperty("OnGround", true);
    private boolean wasFlyingAllowed = false;

    public FlightHack() {
        super("Flight", HackCategory.MOVEMENT);
        this.addProperty(this.onGroundSpoof);
    }

    @Override
    protected void onEnable() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(UpdateListener.class, this);

        this.wasFlyingAllowed = mc.player.getAbilities().allowFlying;
        mc.player.getAbilities().allowFlying = true;
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(UpdateListener.class, this);

        mc.player.getAbilities().allowFlying = this.wasFlyingAllowed;
    }

    public @Override void onUpdate() {
        if (!onGroundSpoof.getState() || Objects.requireNonNull(mc.player).fallDistance < 0.3F) return;

        mc.player.fallDistance = 0.0F;
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }
}
