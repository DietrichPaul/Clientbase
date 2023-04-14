package de.dietrichpaul.clientbase.features.gui.clickgui;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.gui.api.*;
import de.dietrichpaul.clientbase.features.gui.api.Button;
import de.dietrichpaul.clientbase.features.gui.api.Component;
import de.dietrichpaul.clientbase.features.gui.api.Dimension;
import de.dietrichpaul.clientbase.features.gui.api.Image;
import de.dietrichpaul.clientbase.features.gui.api.Label;
import de.dietrichpaul.clientbase.features.gui.api.panel.BoxPanel;
import de.dietrichpaul.clientbase.features.gui.api.panel.GrowGridLayout;
import de.dietrichpaul.clientbase.features.gui.api.panel.DynamicGridLayout;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.properties.Property;
import de.dietrichpaul.clientbase.properties.PropertyGroup;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Map;

public class ClickGuiScreen extends Screen {

    public ClickGuiScreen() {
        super(Text.literal("Einhell Nass-Trockensauger"));
    }

    private void addProperties(BoxPanel propertyList, PropertyGroup group) {
        propertyList.setOrientation(0);
        propertyList.setGap(2);
        propertyList.setMargin(2, 2);
        propertyList.setBackground(0);
        propertyList.setAxis(BoxPanel.Axis.Y);
        for (Property property : group.getProperties()) {
            Component clickGuiComponent = property.getClickGuiComponent();
            if (clickGuiComponent != null)
                propertyList.addComponent(clickGuiComponent);
        }
        for (Map.Entry<String, PropertyGroup> propertyGroup : group.getPropertyGroups().entrySet()) {
            Expandable expandable = new Expandable(new Button(Text.of(propertyGroup.getKey())));
            expandable.getHeader().setBackground(0);
            expandable.getHeader().setMargin(0, 0);
            expandable.setGap(0);
            expandable.getHeader().addComponent( new Image(() -> {
                return new Identifier("clientbase", expandable.isExpanded() ? "gui/collapse-button.png" : "gui/expand-button.png");
            }, new Dimension(10.0F, 6.0F)));
            expandable.setOrientation(0);
            expandable.getHeader().getLabel().setTextX(0);
            expandable.getHeader().addListener(new ActionListener() {
                @Override
                public void mouseClicked(float mouseX, float mouseY, int button) {
                    if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                        if (expandable.isExpanded()) expandable.collapse();
                        else expandable.expand();
                    }
                }
            });
            BoxPanel groupPropertyList = new BoxPanel();
            addProperties(groupPropertyList, propertyGroup.getValue());
            expandable.setContent(groupPropertyList);
            expandable.expand();
            propertyList.addComponent(expandable);
        }
    }

    @Override
    protected void init() {
        DynamicGridLayout mainPanel = new DynamicGridLayout(2, 2);
        mainPanel.setX(50);
        mainPanel.setY(50);
        mainPanel.setWidth(width - 100);
        mainPanel.setHeight(height - 100);
        mainPanel.setBackground(0xc03b3b3b);


        HackCategory[] values = HackCategory.values();
        GrowGridLayout hackCategories = new GrowGridLayout(values.length, 1);
        for (int i = 0; i < values.length; i++) {
            HackCategory category = values[i];
            Button button = new Button(Text.literal(category.toString()));
            button.setBackground(0xff2b2b2b);
            button.getLabel().setTextX(0.5F);
            button.setMargin(5, 5);
            hackCategories.setComponent(i, 0, button);
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
            Button hackButton = new Button(() -> {
                return Text.literal(hack.getName()).styled(style -> style.withFormatting(hack.isToggled() ? Formatting.WHITE : Formatting.GRAY));
            });
            hackButton.getLabel().setTextX(0F);
            hackButton.addListener(new ActionListener() {
                @Override
                public void mouseClicked(float mouseX, float mouseY, int button) {
                    if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                        hack.toggle();
                    } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                        BoxPanel propertyList = new BoxPanel();
                        addProperties(propertyList, hack);
                        mainPanel.setComponent(1, 1, propertyList);
                    }
                }
            });
            hackButton.getLabel().setSize(8);
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
