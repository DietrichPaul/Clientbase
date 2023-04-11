package de.dietrichpaul.clientbase.features.hacks.combat;

import de.dietrichpaul.clientbase.features.clicking.ClickCallback;
import de.dietrichpaul.clientbase.features.clicking.ClickSpoof;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.features.rotation.impl.AimbotRotationSpoof;

public class KillAuraHack extends Hack implements ClickSpoof {

    private final AimbotRotationSpoof aimbot;

    public KillAuraHack() {
        super("KillAura", HackCategory.COMBAT);
        aimbot = new AimbotRotationSpoof(this, addPropertyGroup("Rotations"));
        cb.getRotationEngine().add(aimbot);
        cb.getClickEngine().add(this);
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
        if (Math.random() < 0.75)
            callback.left();
    }
}
