package de.dietrichpaul.clientbase.event.rotate;

import com.darkmagician6.eventapi.events.Event;

public class SendPitchEvent implements Event {

    private float pitch;

    public SendPitchEvent(float pitch) {
        this.pitch = pitch;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}