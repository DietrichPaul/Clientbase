package de.dietrichpaul.clientbase.features.gui.clickgui;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.gui.api.Component;
import de.dietrichpaul.clientbase.features.gui.clickgui.ext.CategoryPanel;
import de.dietrichpaul.clientbase.features.gui.clickgui.ext.HackButton;
import de.dietrichpaul.clientbase.features.gui.clickgui.ext.HackList;
import de.dietrichpaul.clientbase.features.gui.clickgui.ext.PropertyList;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.properties.Property;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {

    public ClickGuiScreen() {
        super(Text.literal("ClickGUI"));
    }

    @Override
    protected void init() {
        float x = 0;
        for (HackCategory category : HackCategory.values()) {
            CategoryPanel categoryPanel = new CategoryPanel(category);
            HackList hackList = new HackList();

            for (Hack hack : ClientBase.getInstance().getHackMap().getHacks()) {
                if (hack.getCategory() != category) {
                    continue;
                }

                HackButton hackPanel = new HackButton(hack);
                hackPanel.setOrientation(-1);
                hackPanel.getHeader().setBackground(0);

                PropertyList propertyList = new PropertyList();
                propertyList.setOrientation(-1);

                for (Property property : hack.getProperties()) {
                    Component clickGuiComponent = property.getClickGuiComponent();
                    if (clickGuiComponent != null)
                        propertyList.addComponent(clickGuiComponent);
                }

                hackPanel.setContent(propertyList);
                hackList.addComponent(hackPanel);
            }

            categoryPanel.setContent(hackList);
            categoryPanel.addToScreen(this);
            categoryPanel.setX(x);
            categoryPanel.reduceSize();
            x += categoryPanel.getWidth() + 10;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
