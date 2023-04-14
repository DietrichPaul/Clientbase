package de.dietrichpaul.clientbase.injection.mixin.click;

import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    public void onHandleInputEvents(CallbackInfo ci) {
        ClientBase.INSTANCE.getClickEngine().update();
    }

}
