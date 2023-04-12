package de.dietrichpaul.clientbase.features.gui.api.panel;

import de.dietrichpaul.clientbase.features.gui.api.Component;
import de.dietrichpaul.clientbase.features.gui.api.Dimension;
import de.dietrichpaul.clientbase.features.gui.api.ParentComponent;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Arrays;
import java.util.Collections;

public class DynamicGridLayout extends ParentComponent {

    private Component[][] components;
    private int gap;

    public void setGap(int gap) {
        this.gap = gap;
    }

    public DynamicGridLayout(int columns, int rows) {
        this.components = new Component[columns][rows];
    }

    public void setComponent(int x, int y, Component component) {
        components[x][y] = component;
    }

    @Override
    protected Dimension getComponentMinimalSize() {
        return null;
    }

    @Override
    protected void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta) {
        Dimension[][] sizes = new Dimension[components.length][components[0].length];

        // x und breite berechnen
        float compX = contentX;
        for (int spalte = 0; spalte < components.length; spalte++) {
            Component[] inhaltSpalte = components[spalte];
            float maxWidth = 0;
            if (spalte == components.length - 1) {
                maxWidth = contentX + contentWidth - compX;
            }
            for (int zeile = 0; zeile < inhaltSpalte.length; zeile++) {
                Component component = inhaltSpalte[zeile];
                if (component == null)
                    continue;
                sizes[spalte][zeile] = component.getMinimalSize();
                if (spalte != components.length - 1) {
                    maxWidth = Math.max(maxWidth, sizes[spalte][zeile].getWidth());
                }
            }
            for (int zeile = 0; zeile < inhaltSpalte.length; zeile++) {
                Component component = inhaltSpalte[zeile];
                if (component == null)
                    continue;
                component.setX(compX);
                component.setWidth(maxWidth);
            }
            compX += maxWidth;
            if (spalte != components.length - 1)
                compX += gap;
        }

        float compY = contentY;
        // y und höhe berechnen
        for (int zeile = 0; zeile < components[0].length; zeile++) {
            float maxHeight = 0;
            if (zeile == components[0].length - 1) {
                maxHeight = contentY + contentHeight - compY;
            }
            for (int spalte = 0; spalte < components.length; spalte++) {
                Component component = components[spalte][zeile];
                if (component == null)
                    continue;
                if (zeile != components[0].length - 1)
                    maxHeight = Math.max(maxHeight, sizes[spalte][zeile].getHeight());
            }
            for (int spalte = 0; spalte < components.length; spalte++) {
                Component component = components[spalte][zeile];
                if (component == null)
                    continue;
                component.setHeight(maxHeight);
                component.setY(compY);
                component.render(matrices, mouseX, mouseY, delta);
            }
            compY += maxHeight;
            if (zeile != components[0].length - 1)
                compY += gap;
        }
    }

    @Override
    public Iterable<Component> children() {
        return Collections.emptyList();
    }
}
