/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.gui.api;

import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class Label extends Component {

    private float textX = 0.5F;
    private float textY = 0.5F;

    private Supplier<Text> textSupplier;

    private float size = 9;

    private int textColor = 0xff000000;
    private boolean dropShadow = false;

    public Label(Supplier<Text> textSupplier) {
        this.textSupplier = textSupplier;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getSize() {
        return size;
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
        return new Dimension(tr.getWidth(textSupplier.get(), size), ClientBase.INSTANCE.getFontList().getVerdana().getLineHeight(size));
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
        float width = tr.getWidth(textSupplier.get().getString(), size);
        if (!dropShadow) {
            tr.render(matrices, text, contentX + (contentWidth - width) * textX, contentY + (contentHeight - tr.getLineHeight(size)) * textY, size, textColor);
        } else {
            tr.renderWithShadow(matrices, text, contentX + (contentWidth - width) * textX, contentY + (contentHeight - tr.getLineHeight(size)) * textY, size, textColor);
        }
    }

}
