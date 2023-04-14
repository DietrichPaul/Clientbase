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
package de.dietrichpaul.clientbase.feature.gui.api;

import net.minecraft.client.gui.screen.Screen;

public abstract class ParentComponent extends Component {

    public abstract Iterable<Component> children();

    @Override
    public void addToScreen(Screen screen) {
        screen.addDrawableChild(new ScreenAdapter(this));
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        for (Component child : children()) {
            if (child.isMouseOver(mouseX, mouseY)) {
                child.mouseClicked(mouseX, mouseY, button);
                return;
            }
        }
    }
}