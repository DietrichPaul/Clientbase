package de.dietrichpaul.clientbase.features.gui.api.panel;

import de.dietrichpaul.clientbase.features.gui.api.Component;
import de.dietrichpaul.clientbase.features.gui.api.Dimension;
import de.dietrichpaul.clientbase.features.gui.api.ParentComponent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class BorderPanel extends ParentComponent {

    private final Component[] components = new Component[Area.values().length];

    private boolean inverse;

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    public boolean isInverse() {
        return inverse;
    }

    public Component getComponent(Area area) {
        if (area == Area.PAGE_START && inverse)
            area = Area.PAGE_END;
        else if (area == Area.PAGE_END && inverse)
            area = Area.PAGE_START;
        if (area == Area.LINE_START && inverse)
            area = Area.LINE_END;
        else if (area == Area.LINE_END && inverse)
            area = Area.LINE_START;
        return components[area.ordinal()];
    }

    public void setComponent(Area area, Component component) {
        //if (component != null)
        //    component.setParent(this);
        components[area.ordinal()] = component;
    }

    @Override
    public Dimension getComponentMinimalSize() {
        Component pageStart = getComponent(Area.PAGE_START);
        Component pageEnd = getComponent(Area.PAGE_END);
        Component lineStart = getComponent(Area.LINE_START);
        Component lineEnd = getComponent(Area.LINE_END);
        Component center = getComponent(Area.CENTER);

        Dimension pageStartPreferredSize = pageStart == null ? null : pageStart.getMinimalSize();
        Dimension pageEndPreferredSize = pageEnd == null ? null : pageEnd.getMinimalSize();
        Dimension lineStartPreferredSize = lineStart == null ? null : lineStart.getMinimalSize();
        Dimension lineEndPreferredSize = lineEnd == null ? null : lineEnd.getMinimalSize();
        Dimension centerPreferredSize = center == null ? null : center.getMinimalSize();

        float panelWidth = 0;

        // page_start/-end
        if (pageStartPreferredSize != null) panelWidth = Math.max(panelWidth, pageStartPreferredSize.getWidth());
        if (pageEndPreferredSize != null) panelWidth = Math.max(panelWidth, pageEndPreferredSize.getWidth());

        // line_start/-end + center
        float lineWidth = 0;
        if (lineStartPreferredSize != null) lineWidth += lineStartPreferredSize.getWidth();
        if (centerPreferredSize != null) lineWidth += centerPreferredSize.getWidth();
        if (lineEndPreferredSize != null) lineWidth += lineEndPreferredSize.getWidth();
        panelWidth = Math.max(panelWidth, lineWidth);

        float panelHeight = 0;
        if (pageStartPreferredSize != null) panelHeight += pageStartPreferredSize.getHeight();
        if (pageEndPreferredSize != null) panelHeight += pageEndPreferredSize.getHeight();

        float lineHeight = 0;
        if (lineStartPreferredSize != null) lineHeight = Math.max(lineHeight, lineStartPreferredSize.getHeight());
        if (lineEndPreferredSize != null) lineHeight = Math.max(lineHeight, lineEndPreferredSize.getHeight());
        if (centerPreferredSize != null) lineHeight = Math.max(lineHeight, centerPreferredSize.getHeight());
        panelHeight += lineHeight;

        return new Dimension(panelWidth, panelHeight);
    }

    @Override
    public void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta) {
        Component pageStart = getComponent(Area.PAGE_START);
        Component pageEnd = getComponent(Area.PAGE_END);
        Component lineStart = getComponent(Area.LINE_START);
        Component lineEnd = getComponent(Area.LINE_END);
        Component center = getComponent(Area.CENTER);

        Dimension pageStartPreferredSize = pageStart == null ? null : pageStart.getMinimalSize();
        Dimension pageEndPreferredSize = pageEnd == null ? null : pageEnd.getMinimalSize();
        Dimension lineStartPreferredSize = lineStart == null ? null : lineStart.getMinimalSize();
        Dimension lineEndPreferredSize = lineEnd == null ? null : lineEnd.getMinimalSize();

        float centerTop = contentY;
        float centerHeight = contentHeight;
        float centerLeft = contentX;
        float centerWidth = contentWidth;

        if (pageStart != null) {
            pageStart.setX(contentX);
            pageStart.setY(contentY);
            pageStart.setWidth(contentWidth);
            pageStart.setHeight(pageStartPreferredSize.getHeight());
            pageStart.render(matrices, mouseX, mouseY, delta);
            centerTop += pageStartPreferredSize.getHeight();
            centerHeight -= pageStartPreferredSize.getHeight();
        }

        if (pageEnd != null) {
            pageEnd.setX(contentX);
            pageEnd.setY(contentY + contentHeight - pageEndPreferredSize.getHeight());
            pageEnd.setWidth(contentWidth);
            pageEnd.setHeight(pageEndPreferredSize.getHeight());
            pageEnd.render(matrices, mouseX, mouseY, delta);
            centerHeight -= pageEndPreferredSize.getHeight();
        }

        if (lineStart != null) {
            lineStart.setX(contentX);
            lineStart.setY(centerTop);
            lineStart.setWidth(lineStartPreferredSize.getWidth());
            lineStart.setHeight(centerHeight);
            lineStart.render(matrices, mouseX, mouseY, delta);
            centerLeft += lineStartPreferredSize.getWidth();
            centerWidth -= lineStartPreferredSize.getWidth();
        }

        if (lineEnd != null) {
            lineEnd.setX(contentX + contentWidth - lineEndPreferredSize.getWidth());
            lineEnd.setY(centerTop);
            lineEnd.setWidth(lineEndPreferredSize.getWidth());
            lineEnd.setHeight(centerHeight);
            lineEnd.render(matrices, mouseX, mouseY, delta);
            centerWidth -= lineEndPreferredSize.getWidth();
        }

        if (center != null) {
            center.setX(centerLeft);
            center.setY(centerTop);
            center.setWidth(centerWidth);
            center.setHeight(centerHeight);
            center.render(matrices, mouseX, mouseY, delta);
        }
    }

    @Override
    public Iterable<Component> children() {
        List<Component> list = new ArrayList<>();
        for (Component component : components) {
            if (component != null) list.add(component);
        }
        return list;
    }

    public enum Area {
        PAGE_START, PAGE_END, LINE_START, LINE_END, CENTER;
    }

}
