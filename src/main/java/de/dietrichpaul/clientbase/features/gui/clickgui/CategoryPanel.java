package de.dietrichpaul.clientbase.features.gui.clickgui;

import de.dietrichpaul.clientbase.features.gui.api.ActionListener;
import de.dietrichpaul.clientbase.features.gui.api.Button;
import de.dietrichpaul.clientbase.features.gui.api.Expandable;
import de.dietrichpaul.clientbase.features.hacks.HackCategory;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class CategoryPanel extends Expandable {

    public CategoryPanel(HackCategory category) {
        super(new Button(Text.literal(category.toString())));
        getHeader().addListener(new ActionListener() {
            @Override
            public void mouseClicked(float mouseX, float mouseY, int button) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                    if (isExpanded()) collapse();
                    else expand();
                }
            }
        });
        getHeader().setBackground(0xfff76b00);
        getHeader().getLabel().setDropShadow(true);
        setBackground(0x80000000);
        setOrientation(-1);
        setDraggable(true);
    }

}
