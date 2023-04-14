package de.dietrichpaul.clientbase.features.gui.api;

import de.dietrichpaul.clientbase.features.gui.api.panel.BorderPanel;
import de.dietrichpaul.clientbase.features.gui.api.panel.BoxPanel;
import de.dietrichpaul.clientbase.features.gui.api.panel.DynamicGridLayout;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import javax.swing.*;
import java.util.function.Supplier;

public class Button extends BoxPanel {

    private final Label label;

    public Button(Supplier<Text> textSupplier) {
        setAxis(Axis.X);
        setMarginX(3);
        setMarginY(3);
        setBackground(0xffcc1f2d);

        label = new Label(textSupplier);
        label.setTextColor(-1);

        addComponent(label);
    }

    public Button(Text text) {
        this(() -> text);
    }

    public Label getLabel() {
        return label;
    }
}
