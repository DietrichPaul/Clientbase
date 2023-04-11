package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.event.PreTickRaytraceListener;
import de.florianmichael.dietrichevents.EventDispatcher;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;updateTargetedEntity(F)V", shift = At.Shift.BEFORE))
    public void onPreRaytrace(CallbackInfo ci) {
        EventDispatcher.g().post(new PreTickRaytraceListener.PreTickRaytraceEvent());
    }
}
