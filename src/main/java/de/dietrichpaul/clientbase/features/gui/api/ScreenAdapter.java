package de.dietrichpaul.clientbase.features.gui.api;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;

public class ScreenAdapter implements Element, Selectable, Drawable {

    private Component component;

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
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        component.reduceSize();
        component.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public SelectionType getType() {
        return SelectionType.NONE;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }
}
