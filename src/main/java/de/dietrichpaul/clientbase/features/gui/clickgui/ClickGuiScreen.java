package de.dietrichpaul.clientbase.features.gui.clickgui;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
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
                hackPanel.getHeader().setBackground(0);


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
