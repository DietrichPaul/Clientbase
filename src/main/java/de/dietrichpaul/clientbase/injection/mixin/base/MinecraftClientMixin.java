package de.dietrichpaul.clientbase.injection.mixin.base;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.util.render.OpenGL;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftClient.class, priority = Integer.MIN_VALUE)
public class MinecraftClientMixin {

    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;inGameHud:Lnet/minecraft/client/gui/hud/InGameHud;", shift = At.Shift.AFTER, ordinal = 0))
    public void onStart(RunArgs args, CallbackInfo ci) {
        ClientBase.INSTANCE.init();
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void onStop(CallbackInfo ci) {
        ClientBase.INSTANCE.stop();
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(boolean tick, CallbackInfo ci) {
        OpenGL.drawFrame();
    }
}
