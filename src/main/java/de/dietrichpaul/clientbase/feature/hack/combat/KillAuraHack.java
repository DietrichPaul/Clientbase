package de.dietrichpaul.clientbase.feature.hack.combat;

import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickCallback;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickSpoof;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.feature.engine.rotation.impl.AimbotRotationSpoof;
import de.dietrichpaul.clientbase.property.impl.EnumProperty;
import de.dietrichpaul.clientbase.property.impl.IntProperty;
import net.minecraft.util.math.MathHelper;

import java.util.function.Predicate;

public class KillAuraHack extends Hack implements UpdateListener, ClickSpoof {
    private final AimbotRotationSpoof aimbot;

    private IntProperty maxCPSProperty = new IntProperty("MaxCPS", 20, 0, 20);
    private IntProperty minCPSProperty = new IntProperty("MinCPS", 20, 0, 20);
    private EnumProperty<SmartClickingMode> smartClickingProperty = new EnumProperty<>("SmartClicking",
            SmartClickingMode.NONE, SmartClickingMode.values(), SmartClickingMode.class);

    private int delay;
    private double partialDelays;

    public KillAuraHack() {
        super("KillAura", HackCategory.COMBAT);
        addProperty(maxCPSProperty);
        addProperty(minCPSProperty);
        addProperty(smartClickingProperty);

        aimbot = new AimbotRotationSpoof(this, addPropertyGroup("Rotations"));
        cb.getRotationEngine().add(aimbot);
        cb.getClickEngine().add(this);
    }

    @Override
    protected void onEnable() {
        cb.getEventDispatcher().subscribe(UpdateListener.class, this);
    }

    @Override
    protected void onDisable() {
        cb.getEventDispatcher().unsubscribe(UpdateListener.class, this);
    }

    @Override
    public boolean canClick() {
        return aimbot.hasTarget();
    }

    @Override
    public int getPriority() {
        return aimbot.getPriority();
    }

    @Override
    public void click(ClickCallback callback) {
        if (delay != 0)
            return;

        if (smartClickingProperty.getValue().click.test(aimbot)) {
            callback.left();
            double delay = 20.0 / MathHelper.lerp(Math.random(), minCPSProperty.getValue(), maxCPSProperty.getValue());
            this.delay = (int) (delay + partialDelays);
            partialDelays += delay - this.delay;
        }
    }

    @Override
    public void onUpdate() {
        if (delay > 0)
            delay--;
    }

    enum SmartClickingMode {

        NONE("None", bot -> true),
        DELAY("Delay", bot -> mc.player.getAttackCooldownProgress(0) >= 1);

        private String name;
        private Predicate<AimbotRotationSpoof> click;

        SmartClickingMode(String name, Predicate<AimbotRotationSpoof> click) {
            this.name = name;
            this.click = click;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
