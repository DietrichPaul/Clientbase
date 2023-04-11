package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.event.Render2DListener;
import de.florianmichael.dietrichevents.EventDispatcher;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    public void onPostRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        EventDispatcher.g().post(new Render2DListener.Render2DEvent(matrices, tickDelta));
    }
}
