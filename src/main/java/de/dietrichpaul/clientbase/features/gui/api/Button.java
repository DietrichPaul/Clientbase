package de.dietrichpaul.clientbase.features.gui.api;

import de.dietrichpaul.clientbase.features.gui.api.panel.BoxPanel;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class Button extends BoxPanel {

    private final Image icon;
    private final Label label;

    public Button(Supplier<Text> textSupplier, Supplier<Identifier> iconSupplier, Dimension dimension) {
        setAxis(Axis.X);
        setGap(4);
        setMarginX(3);
        setMarginY(3);
        setBackground(0xffcc1f2d);

        icon = new Image(iconSupplier, dimension);

        label = new Label(textSupplier);
        label.setTextColor(-1);

        addComponent(icon);
        addComponent(label);
    }

    public Button(Supplier<Text> textSupplier) {
        this(textSupplier, () -> new Identifier("clientbase", "f1.png"), new Dimension(0, 0));
    }

    public Button(Supplier<Identifier> iconSupplier, Dimension dimension) {
        this(Text::empty, iconSupplier, dimension);
    }

    public Button(Text text) {
        this(() -> text);
    }

    public Button(Text text, Supplier<Identifier> icon, Dimension dimension) {
        this(() -> text, icon, dimension);
    }

    public Button(Text text, Identifier icon, Dimension dimension) {
        this(text, () -> icon, dimension);
    }

    public Image getIcon() {
        return icon;
    }

    public Label getLabel() {
        return label;
    }
}
