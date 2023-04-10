package de.dietrichpaul.clientbase.event;

import com.darkmagician6.eventapi.events.Event;

public class JumpEvent implements Event {

    private float yaw;

    public JumpEvent(float yaw) {
        this.yaw = yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }
}