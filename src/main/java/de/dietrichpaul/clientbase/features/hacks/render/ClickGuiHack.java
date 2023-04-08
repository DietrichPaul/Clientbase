package de.dietrichpaul.clientbase.features.hacks.render;

import com.mojang.blaze3d.systems.RenderSystem;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.gui.api.Button;
import de.dietrichpaul.clientbase.features.gui.api.Expandable;
import de.dietrichpaul.clientbase.features.gui.api.panel.BoxPanel;
import de.dietrichpaul.clientbase.features.gui.clickgui.ClickGuiScreen;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.text.Text;

public class ClickGuiHack extends Hack {

    public ClickGuiHack() {
        super("ClickGui", HackCategory.COMBAT);
    }

    @Override
    protected void onEnable() {
        RenderSystem.recordRenderCall(() -> {
            mc.setScreen(new ClickGuiScreen());
        });
        setToggled(false);
    }

}
