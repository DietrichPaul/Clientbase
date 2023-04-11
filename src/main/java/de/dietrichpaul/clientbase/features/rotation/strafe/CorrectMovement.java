package de.dietrichpaul.clientbase.features.rotation.strafe;

import de.dietrichpaul.clientbase.event.StrafeInputListener;
import net.minecraft.client.MinecraftClient;

public abstract class CorrectMovement {

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public abstract void edit(float serverYaw, StrafeInputListener.StrafeInputEvent event);

    public void reset() {
    }
}
