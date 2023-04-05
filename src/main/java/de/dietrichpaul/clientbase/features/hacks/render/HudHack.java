package de.dietrichpaul.clientbase.features.hacks.render;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.dietrichpaul.clientbase.event.Render2DEvent;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.util.render.ColorUtil;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HudHack extends Hack {

    public HudHack() {
        super("HUD", HackCategory.RENDER);
    }

    private void renderActiveHacks(MatrixStack matrices) {
        List<Hack> hacks = new LinkedList<>();
        for (Hack hack : cb.getHackMap().getHacks()) {
            if (hack.isToggled())
                hacks.add(hack);
        }
        hacks.sort(Comparator.comparingInt(h -> -mc.textRenderer.getWidth(h.getName())));

        int width = mc.getWindow().getScaledWidth();
        int y = 0;
        for (Hack hack : hacks) {
            int tw = mc.textRenderer.getWidth(hack.getName());
            DrawableHelper.fill(matrices, width - tw - 4, y, width, y + 12, Integer.MIN_VALUE);
            mc.textRenderer.drawWithShadow(matrices, hack.getName(), width - tw - 2, y + 2, ColorUtil.getRainbow(-y, 0.6F, 1F));
            y += 12;
        }
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        renderActiveHacks(event.getMatrices());
    }

    @Override
    protected void onEnable() {
        EventManager.register(this);
    }

    @Override
    protected void onDisable() {
        EventManager.unregister(this);
    }
}
