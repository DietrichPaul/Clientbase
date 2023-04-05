package de.dietrichpaul.clientbase.event;

import com.darkmagician6.eventapi.events.Event;

public class MoveCameraEvent implements Event {

    private float tickDelta;

    public MoveCameraEvent(float tickDelta) {
        this.tickDelta = tickDelta;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}
