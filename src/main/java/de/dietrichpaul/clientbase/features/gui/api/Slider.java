package de.dietrichpaul.clientbase.features.gui.api;

import de.dietrichpaul.clientbase.features.gui.api.panel.BoxPanel;
import de.dietrichpaul.clientbase.util.FloatSupplier;
import it.unimi.dsi.fastutil.floats.FloatConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class Slider extends BoxPanel {

    private FloatSupplier valueSupplier;
    private FloatSupplier minSupplier;
    private FloatSupplier maxSupplier;
    private FloatConsumer valueConsumer;

    public Slider(Label label, FloatSupplier valueSupplier, FloatSupplier minSupplier, FloatSupplier maxSupplier, FloatConsumer valueConsumer) {
        this.valueSupplier = valueSupplier;
        this.minSupplier = minSupplier;
        this.maxSupplier = maxSupplier;
        this.valueConsumer = valueConsumer;
        setOrientation(0);
        label.setMargin(0, 0);
        label.setSize(7);
        label.setTextColor(-1);
        setBackground(0);
        setMargin(0, 0);
        setGap(1);
        addComponent(label);
        addComponent(new RawSlider());
    }

    class RawSlider extends Component {

        private boolean dragging;

        @Override
        public void mouseClicked(float mouseX, float mouseY, int button) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                dragging = true;
            }
        }

        @Override
        protected Dimension getComponentMinimalSize() {
            return new Dimension(100, 4);
        }

        @Override
        protected void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta) {
            float min = minSupplier.get();
            float max = maxSupplier.get();
            if (dragging) {
                dragging = GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
            }
            if (dragging) {
                valueConsumer.accept(min + (mouseX - contentX) / contentWidth * max);
            }
            fill(matrices, contentX, contentY, contentX + contentWidth, contentY + contentHeight, 0xff2b2b2b);
            float v = valueSupplier.get();
            float rel = (v - min) / (max - min);
            float endX = contentX + contentWidth * rel;
            String valueString = String.valueOf(v);
            fill(matrices, contentX, contentY, endX, contentY + contentHeight, 0xff808080);
            tr.render(matrices, valueString, MathHelper.clamp(endX - tr.getWidth(valueString, 5) / 2, contentX,
                            contentX + contentWidth - tr.getWidth(valueString, 5)),
                    contentY + (contentHeight - tr.getLineHeight(5)) / 2, 5, -1);
        }
    }

}
