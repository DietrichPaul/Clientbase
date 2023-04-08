package de.dietrichpaul.clientbase.features.gui.api;

import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class Label extends Component {

    private float textX = 0.5F;
    private float textY = 0.5F;

    private Supplier<Text> textSupplier;

    private int textColor = 0xff000000;
    private boolean dropShadow = false;

    public Label(Supplier<Text> textSupplier) {
        this.textSupplier = textSupplier;
    }

    public Label(Text text) {
        this(() -> text);
    }

    public void setText(Text text) {
        setText(() -> text);
    }

    public void setText(Supplier<Text> textSupplier) {
        this.textSupplier = textSupplier;
    }

    public Text getText() {
        return textSupplier.get();
    }

    public void setDropShadow(boolean dropShadow) {
        this.dropShadow = dropShadow;
    }

    public boolean isDropShadow() {
        return dropShadow;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextX() {
        return textX;
    }

    public float getTextY() {
        return textY;
    }

    @Override
    protected Dimension getComponentMinimalSize() {
        return new Dimension(tr.getWidth(textSupplier.get()), ClientBase.getInstance().getVerdana().getLineHeight());
    }

    public void setTextX(float textX) {
        this.textX = textX;
    }

    public void setTextY(float textY) {
        this.textY = textY;
    }

    @Override
    protected void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta) {
        Text text = textSupplier.get();
        float width = tr.getWidth(textSupplier.get().getString());
        if (!dropShadow) {
            tr.render(matrices, text, contentX + (contentWidth - width) * textX, contentY + (contentHeight - tr.getLineHeight()) * textY, textColor);
        } else {
            System.out.println("DROP SHADOW");
            tr.renderWithShadow(matrices, text, contentX + (contentWidth - width) * textX, contentY + (contentHeight - tr.getLineHeight()) * textY, textColor);
        }
    }

}
