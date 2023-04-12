package de.dietrichpaul.clientbase.features.gui.clickgui;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.gui.api.Button;
import de.dietrichpaul.clientbase.features.gui.api.Label;
import de.dietrichpaul.clientbase.features.gui.api.panel.BoxPanel;
import de.dietrichpaul.clientbase.features.gui.api.panel.DynamicGridLayout;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {

    public ClickGuiScreen() {
        super(Text.literal("Einhell Nass-Trockensauger"));
    }

    @Override
    protected void init() {
        DynamicGridLayout mainPanel = new DynamicGridLayout(2, 2);
        mainPanel.setX(50);
        mainPanel.setY(50);
        mainPanel.setWidth(width - 100);
        mainPanel.setHeight(height - 100);
        mainPanel.setBackground(0x80000000);

        BoxPanel hackCategories = new BoxPanel();
        hackCategories.setGap(0);
        hackCategories.setAxis(BoxPanel.Axis.X);
        hackCategories.setOrientation(-1);
        hackCategories.setBackground(0xff2b2b2b);
        for (HackCategory category : HackCategory.values()) {
            Button categoryButton = new Button(Text.literal(category.toString()));
            categoryButton.setBackground(0);
            hackCategories.addComponent(categoryButton);
        }

        Label logo = new Label(Text.of(ClientBase.NAME));
        logo.setBackground(0xff373737);
        logo.setDropShadow(true);
        logo.setTextColor(-1);
        logo.setTextX(0.5F);
        logo.setTextY(0.5F);
        logo.setMargin(10, 5);

        BoxPanel hacks = new BoxPanel();
        hacks.setGap(0);
        hacks.setOrientation(-1);
        hacks.setBackground(0xff3b3b3b);
        for (Hack hack : ClientBase.getInstance().getHackMap().getHacks()) {
            Button hackButton = new Button(Text.literal(hack.getName()));
            hackButton.setBackground(0);
            hacks.addComponent(hackButton);
        }

        mainPanel.setComponent(0, 0, logo);
        mainPanel.setComponent(0, 1, hacks);
        mainPanel.setComponent(1, 0, hackCategories);

        mainPanel.addToScreen(this);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
