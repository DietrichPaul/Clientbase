package de.dietrichpaul.clientbase.feature.hack;

public enum HackCategory {

    COMBAT("Combat"), MOVEMENT("Movement"), RENDER("Render"), WORLD("World");

    private final String name;

    HackCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
