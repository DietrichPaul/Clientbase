package de.dietrichpaul.clientbase.features.gui.api;

import de.dietrichpaul.clientbase.features.gui.api.panel.BoxPanel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class Checkbox extends BoxPanel {

    private final RawBox box;
    private final Label label;

    public Checkbox(Supplier<Text> textSupplier) {
        setAxis(Axis.X);
        setGap(4);
        setMarginX(3);
        setMarginY(3);
        setBackground(0);

        box = new RawBox();

        label = new Label(textSupplier);
        label.setTextColor(-1);

        addComponent(box);
        addComponent(label);
    }

    public Checkbox(Text text) {
        this(() -> text);
    }

    public Label getLabel() {
        return label;
    }

    public RawBox getBox() {
        return box;
    }

    public static class RawBox extends Component {

        private int color = 0xffcc1f2d;
        private BooleanSupplier stateSupplier;
        private Image icon;

        public RawBox() {
            icon = new Image(new Identifier("clientbase", "gui/check-button.png"), new Dimension(8, 8));
            icon.setDropShadow(true);
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public BooleanSupplier getStateSupplier() {
            return stateSupplier;
        }

        public void setStateSupplier(BooleanSupplier stateSupplier) {
            this.stateSupplier = stateSupplier;
        }

        @Override
        protected Dimension getComponentMinimalSize() {
            return icon.getMinimalSize();
        }

        @Override
        protected void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta) {
            fill(matrices, contentX, contentY, contentX + contentWidth, contentY + contentHeight, color);
            if (stateSupplier != null && stateSupplier.getAsBoolean()) {
                icon.setWidth(contentWidth);
                icon.setHeight(contentHeight);
                icon.setX(contentX);
                icon.setY(contentY);
                icon.render(matrices, mouseX, mouseY, delta);
            }
        }
    }
}
