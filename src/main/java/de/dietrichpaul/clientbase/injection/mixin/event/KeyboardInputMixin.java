package de.dietrichpaul.clientbase.injection.mixin.event;

import com.darkmagician6.eventapi.EventManager;
import de.dietrichpaul.clientbase.event.StrafeInputEvent;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input {

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/input/KeyboardInput;sneaking:Z", shift = At.Shift.AFTER))
    public void onTick(boolean slowDown, float f, CallbackInfo ci) {
        StrafeInputEvent event = new StrafeInputEvent((int) movementForward, (int) movementSideways, jumping, sneaking);
        EventManager.call(event);
        movementForward = event.getMoveForward();
        movementSideways = event.getMoveSideways();
        jumping = event.isJumping();
        sneaking = event.isSneaking();
    }

}