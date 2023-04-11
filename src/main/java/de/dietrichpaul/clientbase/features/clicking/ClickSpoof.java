package de.dietrichpaul.clientbase.features.clicking;

public interface ClickSpoof {

    boolean isToggled();

    boolean canClick();

    int getPriority();

    void click(ClickCallback callback);
}
