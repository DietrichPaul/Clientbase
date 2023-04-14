package de.dietrichpaul.clientbase.injection.mixin.hack;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.hack.world.XRayHack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Block.class)
public class BlockMixin {

    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void onDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> cir) {
        XRayHack xRay = ClientBase.INSTANCE.getHackList().xRay;
        if (xRay.isToggled()) {
            cir.setReturnValue(xRay.isVisible(state));
        }
    }

}
