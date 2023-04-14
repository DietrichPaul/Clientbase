package de.dietrichpaul.clientbase.feature.gui.api;

import de.dietrichpaul.clientbase.feature.gui.api.panel.BoxPanel;
import net.minecraft.text.Text;

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
