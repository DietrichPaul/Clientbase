package de.dietrichpaul.clientbase.features.gui.api;

import net.minecraft.client.gui.screen.Screen;

public abstract class ParentComponent extends Component {

    public abstract Iterable<Component> children();

    @Override
    public void addToScreen(Screen screen) {
        screen.addDrawableChild(new ScreenAdapter(this));
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        for (Component child : children()) {
            if (child.isMouseOver(mouseX, mouseY)) {
                child.mouseClicked(mouseX, mouseY, button);
                return;
            }
        }
    }
}