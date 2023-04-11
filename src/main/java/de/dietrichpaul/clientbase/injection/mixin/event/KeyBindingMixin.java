package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.event.KeyPressedStateListener;
import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {

    @Inject(method = "isPressed", at = @At("RETURN"), cancellable = true)
    public void onIsPressed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ClientBase.getInstance().getEventDispatcher().post(new KeyPressedStateListener.KeyPressedStateEvent((KeyBinding) (Object) this, cir.getReturnValueZ())).pressed);
    }
}
