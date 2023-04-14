package de.dietrichpaul.clientbase.feature.engine.rotation;

public enum SensitivityFix {

    NONE("None"),
    TICK_BASED("TickBased"),    // 20FPS
    APPROXIMATE("Approximate"), // fps [20;60]
    REAL("Real");               // mouse deltas werden simuliert für reale fps

    private final String name;

    SensitivityFix(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
