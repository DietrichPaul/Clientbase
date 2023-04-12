package de.dietrichpaul.clientbase.features.gui.api;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ScreenAdapter implements Element, Selectable, Drawable {
    private final Component component;

    public ScreenAdapter(Component component) {
        this.component = component;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (component.isMouseOver((float) mouseX, (float) mouseY)) {
            component.mouseClicked((float) mouseX, (float) mouseY, button);
            return true;
        }
        return false;
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        float mX = (float) (component.mc.mouse.getX() * component.mc.getWindow().getScaledWidth() / component.mc.getWindow().getWidth());
        float mY = (float) (component.mc.mouse.getY() * component.mc.getWindow().getScaledHeight() / component.mc.getWindow().getHeight());
        component.setX(MathHelper.clamp(component.getX(), 0, component.mc.getWindow().getScaledWidth() - component.width));
        component.setY(MathHelper.clamp(component.getY(), 0, component.mc.getWindow().getScaledHeight() - component.height));
        component.render(matrices, mX, mY, delta);
    }

    @Override
    public SelectionType getType() {
        return SelectionType.NONE;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }
}
