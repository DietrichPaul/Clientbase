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

import de.dietrichpaul.clientbase.feature.gui.api.panel.BoxPanel;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class Button extends BoxPanel {

    private final Label label;

    public Button(Supplier<Text> textSupplier) {
        setAxis(Axis.X);
        setMarginX(3);
        setMarginY(3);
        setBackground(0xffcc1f2d);

        label = new Label(textSupplier);
        label.setTextColor(-1);

        addComponent(label);
    }

    public Button(Text text) {
        this(() -> text);
    }

    public Label getLabel() {
        return label;
    }
}
