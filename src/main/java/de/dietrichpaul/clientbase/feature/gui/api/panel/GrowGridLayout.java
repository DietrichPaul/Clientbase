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
package de.dietrichpaul.clientbase.feature.gui.api.panel;

import de.dietrichpaul.clientbase.feature.gui.api.Component;
import de.dietrichpaul.clientbase.feature.gui.api.Dimension;
import de.dietrichpaul.clientbase.feature.gui.api.ParentComponent;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Component Größe = width / spalten; height / zeilen.
 */
public class GrowGridLayout extends ParentComponent {

    private Component[][] components;
    private int gap;

    public void setGap(int gap) {
        this.gap = gap;
    }

    public GrowGridLayout(int columns, int rows) {
        this.components = new Component[columns][rows];
    }

    public void setComponent(int x, int y, Component component) {
        components[x][y] = component;
    }

    @Override
    protected Dimension getComponentMinimalSize() {
        float maxWidth = 0;
        float maxHeight = 0;
        for (Component[] spalte : components) {
            for (Component component : spalte) {
                if (component == null) continue;
                Dimension minimalSize = component.getMinimalSize();
                maxWidth = Math.max(maxWidth, minimalSize.getWidth());
                maxHeight = Math.max(maxHeight, minimalSize.getHeight());
            }
        }
        return new Dimension(
                components.length * (maxWidth) + (components.length - 1) * gap,
                components[0].length * (maxHeight) + (components[0].length - 1) * gap
        );
    }

    @Override
    protected void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta) {
        float componentWidth = contentWidth / components.length;
        float componentHeight = contentHeight / components[0].length;

        float compX = x;
        for (int spalte = 0; spalte < components.length; spalte++) {
            float compY = y;
            for (int zeile = 0; zeile < components[0].length; zeile++) {
                Component component = components[spalte][zeile];
                if (component != null) {
                    component.setWidth(componentWidth);
                    component.setHeight(componentHeight);
                    component.setX(compX);
                    component.setY(compY);
                    component.render(matrices, mouseX, mouseY, delta);
                }
                compY += componentHeight;
                if (zeile != components[0].length - 1)
                    compY += gap;
            }
            compX += componentWidth;
            if (spalte != components[0].length - 1)
                compX += gap;
        }
    }

    @Override
    public Iterable<Component> children() {
        List<Component> componentList = new ArrayList<>();
        for (Component[] spalte : components)
            for (Component component : spalte)
                if (component != null) componentList.add(component);
        return componentList;
    }

}
