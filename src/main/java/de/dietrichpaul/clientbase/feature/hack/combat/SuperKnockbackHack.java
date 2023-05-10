package de.dietrichpaul.clientbase.feature.hack.combat;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.KeyPressedStateListener;
import de.dietrichpaul.clientbase.event.TargetPickListener;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

public class SuperKnockbackHack extends Hack implements KeyPressedStateListener, UpdateListener {

    public SuperKnockbackHack() {
        super("SuperKnockback", HackCategory.COMBAT);
    }

    @Override
    protected void onEnable() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(KeyPressedStateListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(UpdateListener.class, this);
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribe(KeyPressedStateListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().unsubscribe(UpdateListener.class, this);
    }

    private int releaseW;

    @Override
    public void onKeyPressedState(KeyPressedStateEvent event) {
        if (event.keyBinding != mc.options.forwardKey)
            return;

        boolean canSprint = mc.options.sprintKey.isPressed();
        Entity target = ClientBase.INSTANCE.getEventDispatcher()
                .post(new TargetPickListener.TargetPickEvent(null)).getTarget();
        if (releaseW == 0 && canSprint && event.pressed && target instanceof LivingEntity living && living.hurtTime == 10) {
            releaseW = 1;
        }
        if (releaseW != 0) {
            event.pressed = false;
        }
    }

    @Override
    public void onUpdate() {
        if (releaseW > 0)
            releaseW--;
    }
}
