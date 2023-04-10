package de.dietrichpaul.clientbase.injection.mixin.event;

import com.darkmagician6.eventapi.EventManager;
import de.dietrichpaul.clientbase.event.StrafeEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin {

    @Redirect(method = "updateVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getYaw()F"))
    public float getStrafeYaw(Entity instance) {
        if (instance instanceof ClientPlayerEntity) {
            StrafeEvent event = new StrafeEvent(instance.getYaw());
            EventManager.call(event);
            return event.getYaw();
        }
        return instance.getYaw();
    }

}
