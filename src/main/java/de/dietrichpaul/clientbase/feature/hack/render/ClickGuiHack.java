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
package de.dietrichpaul.clientbase.feature.hack.render;

import com.mojang.blaze3d.systems.RenderSystem;
import de.dietrichpaul.clientbase.feature.gui.clickgui.ClickGuiScreen;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;

public class ClickGuiHack extends Hack {

    public ClickGuiHack() {
        super("ClickGui", HackCategory.COMBAT);
        doNotSaveState();
    }

    @Override
    protected void onEnable() {
        RenderSystem.recordRenderCall(() -> mc.setScreen(new ClickGuiScreen()));

        setToggled(false);
    }
}
