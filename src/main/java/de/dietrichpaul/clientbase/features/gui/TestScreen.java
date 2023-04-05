package de.dietrichpaul.clientbase.features.gui;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.gui.api.ActionListener;
import de.dietrichpaul.clientbase.features.gui.api.Button;
import de.dietrichpaul.clientbase.features.gui.api.Dimension;
import de.dietrichpaul.clientbase.features.gui.api.Expandable;
import de.dietrichpaul.clientbase.features.gui.api.panel.BoxPanel;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class TestScreen extends Screen {

    public TestScreen() {
        super(Text.literal("Test"));
    }

    @Override
    protected void init() {
        Button button = new Button(Text.of("Trolling 123"));
        button.getIcon().setDropShadow(true);
        button.getLabel().setDropShadow(true);
        button.setInverse(true);
        button.setMarginY(4);
        button.setMarginX(4);
        button.setGap(4);

        BoxPanel content = new BoxPanel();
        content.setAxis(BoxPanel.Axis.Y);
        content.setMarginX(2);
        content.setMarginY(2);
        content.setGap(2);
        content.setBackground(0);
        content.setOrientation(-1);

        for (Hack hack : ClientBase.getInstance().getHackMap().getHacks()) {
            Expandable hackButton = new Expandable(new Button(Text.of(hack.getName())));
            hackButton.getHeader().setInverse(true);
            hackButton.getHeader().setMargin(1, 1);
            hackButton.getHeader().setBackground(0);
            hackButton.setMarginY(0);
            hackButton.setMarginX(0);
            hackButton.setGap(0);
            hackButton.setOrientation(-1);

            hackButton.getHeader().addListener(new ActionListener() {
                @Override
                public void mouseClicked(float mouseX, float mouseY, int button) {
                    if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                        if (hackButton.isExpanded()) hackButton.collapse();
                        else hackButton.expand();
                    }
                }
            });

            BoxPanel properties = new BoxPanel();
            properties.setAxis(BoxPanel.Axis.Y);
            properties.setMarginX(2);
            properties.setMarginY(2);
            properties.setGap(1);
            properties.setBackground(0);
            properties.setOrientation(-1);

            for (int i = 0; i < 10; i++) {
                Button propertyButton = new Button(Text.of("Property-" + i));
                propertyButton.setBackground(0);
                propertyButton.setMarginX(1);
                propertyButton.setMarginY(1);
                properties.addComponent(propertyButton);
            }
            hackButton.setContent(properties);

            content.addComponent(hackButton);
        }

        Expandable tab = new Expandable(button);
        tab.setDraggable(true);
        tab.setOrientation(-1);
        tab.setGap(0);
        tab.setBackground(0x80000000);
        tab.setContent(content);

        button.addListener(new ActionListener() {
            @Override
            public void mouseClicked(float mouseX, float mouseY, int button) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                    if (tab.isExpanded()) tab.collapse();
                    else tab.expand();
                }
            }
        });

        button.getIcon().updateImage(() -> {
            return tab.isExpanded() ? new Identifier("clientbase", "gui/collapse-button.png") : new Identifier("clientbase", "gui/expand-button.png");
        }, new Dimension(12, 7.5f));

        tab.addToScreen(this);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
