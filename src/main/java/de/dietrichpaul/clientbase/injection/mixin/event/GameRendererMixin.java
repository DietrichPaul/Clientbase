package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.event.MoveCameraListener;
import de.dietrichpaul.clientbase.event.RaytraceListener;
import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "updateTargetedEntity", at = @At("HEAD"), cancellable = true)
    public void onUpdateTargetedEntity(float tickDelta, CallbackInfo ci) {
        final RaytraceListener.RaytraceEvent raytraceEvent = ClientBase.getInstance().getEventDispatcher().post(new RaytraceListener.RaytraceEvent(tickDelta));
        if (raytraceEvent.isCancelled()) ci.cancel();
    }

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;skipGameRender:Z", shift = At.Shift.BEFORE))
    public void onRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        ClientBase.getInstance().getEventDispatcher().post(new MoveCameraListener.MoveCameraEvent(tickDelta));
    }
}
