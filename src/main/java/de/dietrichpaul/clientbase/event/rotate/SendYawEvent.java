package de.dietrichpaul.clientbase.event.rotate;

import com.darkmagician6.eventapi.events.Event;

public class SendYawEvent implements Event {

    private float yaw;

    public SendYawEvent(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}