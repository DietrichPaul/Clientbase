package de.dietrichpaul.clientbase.event;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.util.math.MatrixStack;

public class Render2DEvent implements Event {

    private final MatrixStack matrices;
    private final float tickDelta;

    public Render2DEvent(MatrixStack matrices, float tickDelta) {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }

    public MatrixStack getMatrices() {
        return matrices;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}
