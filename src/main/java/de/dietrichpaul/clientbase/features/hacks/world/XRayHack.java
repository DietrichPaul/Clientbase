package de.dietrichpaul.clientbase.features.hacks.world;

import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;

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
