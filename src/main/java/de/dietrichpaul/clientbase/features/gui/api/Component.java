package de.dietrichpaul.clientbase.features.gui.api;

import de.dietrichpaul.clientbase.util.render.Renderer2D;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.util.LinkedList;
import java.util.List;

public abstract class Component extends Renderer2D implements ActionListener {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    protected float contentX;
    protected float contentY;
    protected float contentWidth;
    protected float contentHeight;

    private float marginX = 0, marginY = 0;

    private int background;

    private List<ActionListener> listeners = new LinkedList<>();

    protected MinecraftClient mc = MinecraftClient.getInstance();
    protected TextRenderer tr = mc.textRenderer;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMarginX() {
        return marginX;
    }

    public void setMarginX(float marginX) {
        this.marginX = marginX;
    }

    public float getMarginY() {
        return marginY;
    }

    public void setMarginY(float marginY) {
        this.marginY = marginY;
    }

    public void setMargin(float x, float y) {
        setMarginX(x);
        setMarginY(y);
    }

    public Dimension getMinimalSize() {
        return getComponentMinimalSize().add(marginX * 2, marginY * 2);
    }

    public boolean isMouseOver(float mouseX, float mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    protected abstract Dimension getComponentMinimalSize();

    protected abstract void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta);

    public void addToScreen(Screen screen) {
        throw new UnsupportedOperationException();
    }

    public final void render(MatrixStack matrices, float mouseX, float mouseY, float delta) {
        if (background != 0) {
            fill(matrices, x, y, x + width, y + height, background);
        }
        contentX = x + marginX;
        contentY = y + marginY;
        contentWidth = width - 2 * marginX;
        contentHeight = height - 2 * marginY;
        renderComponent(matrices, mouseX, mouseY, delta);
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getBackground() {
        return background;
    }

    public void addListener(ActionListener listener) {
        listeners.add(listener);
    }

    public List<ActionListener> getListeners() {
        return listeners;
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int button) {
        for (ActionListener listener : listeners)
            listener.mouseClicked(mouseX, mouseY, button);
    }

    public void reduceSize() {
        Dimension minimalSize = getMinimalSize();
        setWidth(minimalSize.getWidth());
        setHeight(minimalSize.getHeight());
    }

}
