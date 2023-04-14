package de.dietrichpaul.clientbase.feature.gui.api.panel;

import de.dietrichpaul.clientbase.feature.gui.api.Component;
import de.dietrichpaul.clientbase.feature.gui.api.Dimension;
import de.dietrichpaul.clientbase.feature.gui.api.ParentComponent;
import net.minecraft.client.util.math.MatrixStack;

import java.util.LinkedList;
import java.util.List;

/**
 * Orientation: -1 -> stretch children
 */
public class BoxPanel extends ParentComponent {

    private List<Component> children = new LinkedList<>();

    private float orientation = 0.5F;
    private Axis axis = Axis.Y;

    private boolean inverse;

    private float gap = 5;

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    public boolean isInverse() {
        return inverse;
    }

    public float getGap() {
        return gap;
    }

    public void setGap(float gap) {
        this.gap = gap;
    }

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public float getOrientation() {
        return orientation;
    }

    public void removeComponent(int i) {
        this.children.remove(i);
    }

    public void setComponent(int i, Component component) {
        this.children.set(i, component);
    }

    public void addComponent(int i, Component component) {
        this.children.add(i, component);
    }

    public void addComponent(Component component) {
        this.children.add(component);
    }

    @Override
    protected Dimension getComponentMinimalSize() {
        float panelWidth = 0;
        float panelHeight = 0;
        for (int i = 0; i < children.size(); i++) {
            Component component = children.get(inverse ? children.size() - i - 1 : i);
            Dimension size = component.getMinimalSize();
            if (size.getWidth() == 0 && size.getHeight() == 0)
                continue;
            if (axis == Axis.X) {
                panelWidth += size.getWidth();
                panelHeight = Math.max(panelHeight, size.getHeight());
                if (i != children.size() - 1)
                    panelWidth += gap;
            } else if (axis == Axis.Y) {
                panelHeight += size.getHeight();
                panelWidth = Math.max(panelWidth, size.getWidth());
                if (i != children.size() - 1)
                    panelHeight += gap;
            }
        }
        return new Dimension(panelWidth, panelHeight);
    }

    @Override
    protected void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta) {
        float compX = 0;
        float compY = 0;
        for (int i = 0; i < children.size(); i++) {
            Component child = children.get(inverse ? children.size() - i - 1: i);
            Dimension size = child.getMinimalSize();
            if (size.getWidth() == 0 && size.getHeight() == 0)
                continue;
            if (getOrientation() == -1) {
                if (axis == Axis.X) {
                    child.setWidth(size.getWidth());
                    child.setHeight(contentHeight);
                } else {
                    child.setWidth(contentWidth);
                    child.setHeight(size.getHeight());
                }
            } else {
                child.setWidth(size.getWidth());
                child.setHeight(size.getHeight());
            }
            float orientation = getOrientation() == -1 ? 0 : getOrientation();
            if (axis == Axis.X) {
                child.setX(contentX + compX);
                child.setY(contentY + (contentHeight - size.getHeight()) * orientation);
                compX += size.getWidth();
                if (i != children.size() - 1) {
                    compX += gap;
                }
            } else if (axis == Axis.Y) {
                child.setX(contentX + (contentWidth - size.getWidth()) * orientation);
                child.setY(contentY + compY);
                compY += size.getHeight();
                if (i != children.size() - 1) {
                    compY += gap;
                }
            }
            child.render(matrices, mouseX, mouseY, delta);
        }
    }

    @Deprecated
    public List<Component> getChildren() {
        return children;
    }

    @Override
    public Iterable<Component> children() {
        return this.children;
    }

    public enum Axis {
        X, Y
    }
}
