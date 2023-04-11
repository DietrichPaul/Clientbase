package de.dietrichpaul.clientbase.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class RaytraceEvent extends EventCancellable {

    private float tickDelta;

    public RaytraceEvent(float tickDelta) {
        this.tickDelta = tickDelta;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}
