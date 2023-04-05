package de.dietrichpaul.clientbase.features.gui.api;

public abstract class ParentComponent extends Component {

    public abstract Iterable<Component> children();

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