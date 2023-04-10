package de.dietrichpaul.clientbase.event.rotate;

import com.darkmagician6.eventapi.events.Event;

public class RotateGetEvent implements Event {

    private float yaw, pitch;

    public RotateGetEvent(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}