package de.dietrichpaul.clientbase.event.rotate;

import com.darkmagician6.eventapi.events.Event;

public class RotateSetEvent implements Event {

    private float yaw, pitch;
    private boolean isYaw, isPitch;

    public RotateSetEvent(float yaw, float pitch, boolean isYaw, boolean isPitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.isYaw = isYaw;
        this.isPitch = isPitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean isYaw() {
        return isYaw;
    }

    public boolean isPitch() {
        return isPitch;
    }
}