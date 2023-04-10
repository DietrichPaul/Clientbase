package de.dietrichpaul.clientbase.features.gui.api;

import de.dietrichpaul.clientbase.features.gui.api.panel.BoxPanel;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class Expandable extends BoxPanel {

    private boolean draggable;

    private boolean dragging;
    private float startX;
    private float startY;

    private Button header;
    private Component content;

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public Expandable(Button header) {
        this.header = header;
        header.getLabel().setTextX(1);
        setGap(0);
        header.addListener(new ActionListener() {
            @Override
            public void mouseClicked(float mouseX, float mouseY, int button) {
                if (draggable && button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    dragging = true;
                    startX = mouseX - getX();
                    startY = mouseY - getY();
                }
            }
        });
        addComponent(header);
    }

    @Override
    protected Dimension getComponentMinimalSize() {
        if (content != null)
            return super.getComponentMinimalSize().union(new Dimension(content.getMinimalSize().getWidth(), 0));
        return super.getComponentMinimalSize();
    }

    public Button getHeader() {
        return header;
    }

    public void collapse() {
        if (!isExpanded())
            return;
        removeComponent(1);
    }

    public void expand() {
        if (isExpanded() || content == null)
            return;
        addComponent(content);
    }

    public boolean isExpanded() {
        return getChildren().size() == 2;
    }

    public void setContent(Component content) {
        this.content = content;
    }

    @Override
    public void addToScreen(Screen screen) {
        screen.addDrawableChild(new ScreenAdapter(this));
    }

    @Override
    protected void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta) {
        if (dragging) {
            dragging = GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        }
        if (dragging) {
            setX(mouseX - startX);
            setY(mouseY - startY);
        }
        super.renderComponent(matrices, mouseX, mouseY, delta);
    }
}
