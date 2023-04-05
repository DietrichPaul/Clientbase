package de.dietrichpaul.clientbase.features.hacks.combat;

import com.darkmagician6.eventapi.EventManager;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.rotation.RotationSpoof;
import de.dietrichpaul.clientbase.util.MathUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class KillAuraHack extends Hack implements RotationSpoof {

    public KillAuraHack() {
        super("KillAura", HackCategory.COMBAT);
        engine.add(this);
    }

    private final List<Entity> targets = new LinkedList<>();

    @Override
    protected void onEnable() {
        EventManager.register(this);
    }

    @Override
    protected void onDisable() {
        EventManager.unregister(this);
    }

    @Override
    public boolean pickTarget() {
        targets.clear();
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof ClientPlayerEntity)
                continue;

            targets.add(entity);
        }
        targets.sort(Comparator.comparingDouble(mc.player::distanceTo));
        return !targets.isEmpty();
    }

    @Override
    public void rotate(float[] rotations, boolean tick, float partialTicks) {
        Entity victim = targets.get(0);
        Vec3d camera = mc.player.getCameraPosVec(partialTicks);
        Vec3d hitVec = victim.getCameraPosVec(partialTicks);

        MathUtil.getRotations(camera, hitVec, rotations);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean canInterpolate() {
        return false;
    }
}
