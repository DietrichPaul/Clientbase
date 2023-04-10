package de.dietrichpaul.clientbase.features.hacks.combat;

import com.darkmagician6.eventapi.EventManager;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.features.rotation.RotationSpoof;
import de.dietrichpaul.clientbase.features.rotation.impl.AimbotRotationSpoof;
import de.dietrichpaul.clientbase.util.MathUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class KillAuraHack extends Hack {

    private final AimbotRotationSpoof aimbot;

    public KillAuraHack() {
        super("KillAura", HackCategory.COMBAT);
        aimbot = new AimbotRotationSpoof(this, addPropertyGroup("Rotations"));
        cb.getRotationEngine().add(aimbot);
    }

    @Override
    protected void onEnable() {
        EventManager.register(this);
    }

    @Override
    protected void onDisable() {
        EventManager.unregister(this);
    }

}
