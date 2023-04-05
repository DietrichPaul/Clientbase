package de.dietrichpaul.clientbase.injection.mixin.event;

import com.darkmagician6.eventapi.EventManager;
import de.dietrichpaul.clientbase.event.KeyPressedStateEvent;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {

    @Inject(method = "isPressed", at = @At("RETURN"), cancellable = true)
    public void onIsPressed(CallbackInfoReturnable<Boolean> cir) {
        KeyPressedStateEvent event = new KeyPressedStateEvent((KeyBinding) (Object) this, cir.getReturnValueZ());
        EventManager.call(event);
        cir.setReturnValue(event.isPressed());
    }

}
