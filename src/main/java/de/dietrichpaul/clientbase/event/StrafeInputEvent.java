package de.dietrichpaul.clientbase.event;

import com.darkmagician6.eventapi.events.Event;

public class StrafeInputEvent implements Event {

    private int moveForward;
    private int moveSideways;
    private boolean jumping;
    private boolean sneaking;

    public StrafeInputEvent(int moveForward, int moveSideways, boolean jumping, boolean sneaking) {
        this.moveForward = moveForward;
        this.moveSideways = moveSideways;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public int getMoveForward() {
        return moveForward;
    }

    public void setMoveForward(int moveForward) {
        this.moveForward = moveForward;
    }

    public int getMoveSideways() {
        return moveSideways;
    }

    public void setMoveSideways(int moveSideways) {
        this.moveSideways = moveSideways;
    }
}