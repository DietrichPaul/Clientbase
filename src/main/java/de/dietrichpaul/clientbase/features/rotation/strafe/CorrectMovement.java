package de.dietrichpaul.clientbase.features.rotation.strafe;

import de.dietrichpaul.clientbase.event.StrafeInputEvent;
import net.minecraft.client.MinecraftClient;

public abstract class CorrectMovement {

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public abstract void edit(float serverYaw, StrafeInputEvent event);

    public void reset() {
    }
}
