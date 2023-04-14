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
        RenderSystem.recordRenderCall(() -> {
            mc.setScreen(new ClickGuiScreen());
        });
        setToggled(false);
    }

}
