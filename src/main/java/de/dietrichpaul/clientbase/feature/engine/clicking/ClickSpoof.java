package de.dietrichpaul.clientbase.feature.engine.clicking;

public interface ClickSpoof {

    boolean isToggled();

    boolean canClick();

    int getPriority();

    void click(ClickCallback callback);
}
