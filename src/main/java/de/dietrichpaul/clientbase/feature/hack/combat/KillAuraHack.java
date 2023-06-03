/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.hack.combat;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.TargetPickListener;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickCallback;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickSpoof;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.feature.engine.rotation.impl.AimbotRotationSpoof;
import de.dietrichpaul.clientbase.property.impl.EnumProperty;
import de.dietrichpaul.clientbase.property.impl.IntProperty;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class KillAuraHack extends Hack implements UpdateListener,TargetPickListener, ClickSpoof {
    private final AimbotRotationSpoof aimbot;

    private IntProperty maxCPSProperty = new IntProperty("MaxCPS", 20, 0, 20);
    private IntProperty minCPSProperty = new IntProperty("MinCPS", 20, 0, 20);
    private IntProperty failRateProperty = new IntProperty("FailRate", 0, 0, 100);
    private EnumProperty<SmartClickingMode> smartClickingProperty = new EnumProperty<>("SmartClicking",
            SmartClickingMode.NONE, SmartClickingMode.values(), SmartClickingMode.class);

    private double cps;
    private int delay;
    private double partialDelays;

    public KillAuraHack() {
        super("KillAura", HackCategory.COMBAT);
        addProperty(maxCPSProperty);
        addProperty(minCPSProperty);
        addProperty(failRateProperty);
        addProperty(smartClickingProperty);

        aimbot = new AimbotRotationSpoof(this, addPropertyGroup("Rotations"));
        ClientBase.INSTANCE.getRotationEngine().add(aimbot);
        ClientBase.INSTANCE.getClickEngine().add(this);
    }

    @Override
    protected void onEnable() {
        cps = MathHelper.lerp(Math.random(), minCPSProperty.getValue(), maxCPSProperty.getValue());
        ClientBase.INSTANCE.getEventDispatcher().subscribe(UpdateListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(TargetPickListener.class, this, this::getPriority);
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribe(UpdateListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().unsubscribe(TargetPickListener.class, this);
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

        HitResult crosshairTarget = mc.crosshairTarget;
        if (!(crosshairTarget instanceof EntityHitResult) && Math.random() * 100 > failRateProperty.getValue()) {
            return;
        }

        if (smartClickingProperty.getValue().click.test(aimbot)) {
            callback.left();
            double delay = 20.0 / cps;
            this.delay = (int) (delay + partialDelays);
            partialDelays += delay - this.delay;
        }
    }

    @Override
    public void onUpdate() {
        if (ThreadLocalRandom.current().nextGaussian() > 0) {
            cps = MathHelper.lerp(Math.random(), minCPSProperty.getValue(), maxCPSProperty.getValue());
            partialDelays = 0;
        }
        if (delay > 0)
            delay--;
    }

    @Override
    public void onPickTarget(TargetPickEvent event) {
        if (aimbot.hasTarget())
            event.setTarget(aimbot.getPrimaryTarget());
    }

    enum SmartClickingMode {
        NONE("None", bot -> true),
        DELAY("Delay", bot -> mc.player.getAttackCooldownProgress(0) >= 1);

        private final String name;
        private final Predicate<AimbotRotationSpoof> click;

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
