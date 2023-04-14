/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
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
