package de.dietrichpaul.clientbase.features.hacks.render;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.Render2DEvent;
import de.dietrichpaul.clientbase.features.gui.api.font.FontAtlas;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.util.render.ColorUtil;
import de.dietrichpaul.clientbase.util.render.Renderer2D;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HudHack extends Hack {

    public HudHack() {
        super("HUD", HackCategory.RENDER);
    }

    private void renderActiveHacks(MatrixStack matrices) {
        FontAtlas font = ClientBase.getInstance().getVerdana();
        Renderer2D.fill(matrices, 2, 2, 6 + 2 * font.getWidth(ClientBase.NAME), 6 + 2 * font.getLineHeight(), 0x80ff0000);
        matrices.scale(2, 2, 2);
        font.render(matrices, ClientBase.NAME, 4 / 2F, 4 / 2F, -1);
        matrices.scale(0.5F, 0.5F, 0.5F);

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
