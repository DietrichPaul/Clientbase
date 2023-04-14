package de.dietrichpaul.clientbase.feature.gui.api;

public class Dimension {

    private float width;

    private float height;

    public Dimension(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public Dimension add(float x, float y) {
        return new Dimension(width + x, height + y);
    }

    public Dimension union(Dimension dimension) {
        return new Dimension(Math.max(width, dimension.width), Math.max(height, dimension.height));
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

    @Override
    public String toString() {
        return "Dimension{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
