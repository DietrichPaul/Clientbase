package de.dietrichpaul.clientbase.features.rotation;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.properties.PropertyGroup;
import de.dietrichpaul.clientbase.properties.impl.BooleanProperty;
import de.dietrichpaul.clientbase.properties.impl.EnumProperty;
import de.dietrichpaul.clientbase.properties.impl.FloatProperty;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public abstract class RotationSpoof {

    public static final RotationEngine engine = ClientBase.getInstance().getRotationEngine();

    protected MinecraftClient mc = MinecraftClient.getInstance();
    protected ClientBase cb = ClientBase.getInstance();

    private final FloatProperty minYawSpeed = new FloatProperty("Min Yaw Speed", 20, 1, 180);
    private final FloatProperty maxYawSpeed = new FloatProperty("Max Yaw Speed", 30, 1, 180);
    private final FloatProperty minPitchSpeed = new FloatProperty("Min Pitch Speed", 15, 1, 180);
    private final FloatProperty maxPitchSpeed = new FloatProperty("Max Pitch Speed", 20, 1, 180);
    private final BooleanProperty rotateBack = new BooleanProperty("Rotate Back", false);
    private final BooleanProperty lockView = new BooleanProperty("Lock View", false);
    private final EnumProperty<SensitivityFix> sensitivityFixProperty = new EnumProperty<>("SensitivityFix",
            SensitivityFix.APPROXIMATE, SensitivityFix.values(), SensitivityFix.class);

    public RotationSpoof(PropertyGroup propertyGroup) {
        propertyGroup.addProperty(minYawSpeed);
        propertyGroup.addProperty(maxYawSpeed);
        propertyGroup.addProperty(minPitchSpeed);
        propertyGroup.addProperty(maxPitchSpeed);
        propertyGroup.addProperty(rotateBack);
        propertyGroup.addProperty(lockView);
        propertyGroup.addProperty(sensitivityFixProperty);
    }

    /**
     * Sucht target und gibt zurück, ob eins gefunden wurde.
     * Für die implementation: Es sei schlau, würde das Target gespeichert werden,
     * da folgend die #rotate(float[])-Methode aufgerufen wird.
     *
     * @return ob ein Target gefunden wurde
     */
    public abstract boolean pickTarget();

    public abstract void rotate(float[] rotations, boolean tick, float partialTicks);

    public abstract boolean isToggled();

    public abstract int getPriority();

    public float getYawSpeed() {
        return MathHelper.lerp((float) Math.random(), minYawSpeed.getValue(), maxYawSpeed.getValue());
    }

    public float getPitchSpeed() {
        return MathHelper.lerp((float) Math.random(), minPitchSpeed.getValue(), maxPitchSpeed.getValue());
    }

    public boolean rotateBack() {
        return rotateBack.getState();
    }

    public boolean lockView() {
        return lockView.getState();
    }

    public SensitivityFix getSensitivityFix() {
        return sensitivityFixProperty.getValue();
    }
}
