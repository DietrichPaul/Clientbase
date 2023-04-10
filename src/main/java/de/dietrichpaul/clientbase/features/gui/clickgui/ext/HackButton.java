package de.dietrichpaul.clientbase.features.gui.clickgui.ext;

import de.dietrichpaul.clientbase.features.gui.api.ActionListener;
import de.dietrichpaul.clientbase.features.gui.api.Button;
import de.dietrichpaul.clientbase.features.gui.api.Expandable;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class HackButton extends Expandable {

    public HackButton(Hack hack) {
        super(new Button(() -> Text.literal(hack.getName()).styled(style
                -> style.withFormatting(hack.isToggled() ? Formatting.WHITE : Formatting.GRAY))));
        getHeader().setBackground(0);
        getHeader().addListener(new ActionListener() {
            @Override
            public void mouseClicked(float mouseX, float mouseY, int button) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    hack.toggle();
                } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                    if (isExpanded()) collapse();
                    else expand();
                }
            }
        });
    }

}
