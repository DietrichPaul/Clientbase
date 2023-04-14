package de.dietrichpaul.clientbase.features.hacks.render;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.Render2DListener;
import de.dietrichpaul.clientbase.features.gui.api.font.FontAtlas;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import de.dietrichpaul.clientbase.util.render.ColorUtil;
import de.dietrichpaul.clientbase.util.render.api.Renderer2D;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HudHack extends Hack implements Render2DListener {

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
        hacks.sort(Comparator.comparingDouble(h -> -font.getWidth(h.getName())));

        int width = mc.getWindow().getScaledWidth();
        float y = 0;
        for (Hack hack : hacks) {
            float tw = font.getWidth(hack.getName());
            Renderer2D.fill(matrices, width - tw - 4, y, width, y + font.getLineHeight() + 4, Integer.MIN_VALUE);
            font.renderWithShadow(matrices, hack.getName(), width - tw - 2, y + 2, ColorUtil.getRainbow(-y, 0.6F, 1F));
            y += font.getLineHeight() + 4;
        }
    }

    @Override
    public void onRender2D(MatrixStack matrices, float tickDelta) {
        renderActiveHacks(matrices);
    }

    @Override
    protected void onEnable() {
        ClientBase.getInstance().getEventDispatcher().subscribe(Render2DListener.class, this);
    }

    @Override
    protected void onDisable() {
        ClientBase.getInstance().getEventDispatcher().unsubscribe(Render2DListener.class, this);
    }
}
