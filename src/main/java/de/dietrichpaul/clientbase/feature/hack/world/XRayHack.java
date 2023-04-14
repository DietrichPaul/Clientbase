package de.dietrichpaul.clientbase.feature.hack.world;

import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class XRayHack extends Hack {

    public XRayHack() {
        super("XRay", HackCategory.WORLD);
    }

    @Override
    protected void onEnable() {
        mc.worldRenderer.reload();
    }

    @Override
    protected void onDisable() {
        mc.worldRenderer.reload();
    }

    public boolean isVisible(BlockState block) {
        return block.getBlock() == Blocks.DIAMOND_ORE;
    }
}
