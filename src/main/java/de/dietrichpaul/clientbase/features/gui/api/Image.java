package de.dietrichpaul.clientbase.features.gui.api;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class Image extends Component {

    private Supplier<Identifier> icon;
    private Dimension preferredSize;
    private boolean dropShadow;

    public Image(Supplier<Identifier> icon, Dimension preferredSize) {
        setMarginX(0);
        setMarginY(0);
        this.icon = icon;
        this.preferredSize = preferredSize;
    }

    public Image(Identifier icon, Dimension preferredSize) {
        this(() -> icon, preferredSize);
    }

    @Override
    protected Dimension getComponentMinimalSize() {
        return preferredSize;
    }

    public void updateImage(Supplier<Identifier> icon, Dimension preferredSize) {
        this.icon = icon;
        this.preferredSize = preferredSize;
    }

    public void updateIMage(Identifier icon, Dimension preferredSize) {
        updateImage(() -> icon, preferredSize);
    }

    public boolean isDropShadow() {
        return dropShadow;
    }

    public void setDropShadow(boolean dropShadow) {
        this.dropShadow = dropShadow;
    }

    @Override
    protected void renderComponent(MatrixStack matrices, float mouseX, float mouseY, float delta) {
        Identifier identifier = icon.get();
        RenderSystem.setShaderTexture(0, identifier);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        mc.getTextureManager().getTexture(identifier).setFilter(true, false);
        if (dropShadow) {
            RenderSystem.setShaderColor(0, 0, 0, 0.5f);
            drawTexture(matrices, contentX + 0.5f, contentY + 1, contentWidth, contentHeight);
        }
        RenderSystem.setShaderColor(1,1,1,1);
        drawTexture(matrices, contentX, contentY, contentWidth, contentHeight);
    }
}
