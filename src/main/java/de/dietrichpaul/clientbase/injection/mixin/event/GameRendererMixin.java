package de.dietrichpaul.clientbase.injection.mixin.event;

import com.darkmagician6.eventapi.EventManager;
import de.dietrichpaul.clientbase.event.MoveCameraEvent;
import de.dietrichpaul.clientbase.event.RaytraceEvent;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "updateTargetedEntity", at = @At("HEAD"), cancellable = true)
    public void onUpdateTargetedEntity(float tickDelta, CallbackInfo ci) {
        RaytraceEvent event = new RaytraceEvent(tickDelta);
        EventManager.call(event);
        if (event.isCancelled())
            ci.cancel();
    }

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;skipGameRender:Z", shift = At.Shift.BEFORE))
    public void onRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        EventManager.call(new MoveCameraEvent(tickDelta));
    }

}
