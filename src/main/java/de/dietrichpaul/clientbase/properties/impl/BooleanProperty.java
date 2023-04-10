package de.dietrichpaul.clientbase.properties.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.gui.api.ActionListener;
import de.dietrichpaul.clientbase.features.gui.api.Checkbox;
import de.dietrichpaul.clientbase.features.gui.api.Component;
import de.dietrichpaul.clientbase.properties.Property;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import org.lwjgl.glfw.GLFW;

public class BooleanProperty extends Property {

    private boolean state;

    public BooleanProperty(String name, boolean state) {
        super(name);
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        reportChanges();
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(state);
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element != null)
            state = element.getAsBoolean();
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                literal("set").then(
                        argument("value", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    setState(BoolArgumentType.getBool(ctx, "value"));
                                    cb.sendChatMessage(Text.literal(getName() + " was set to " + state));
                                    return 1;
                                })
                )
        );
    }

    @Override
    public Component getClickGuiComponent() {
        Checkbox checkbox = new Checkbox(Text.literal(getName()));
        checkbox.getBox().setColor(ClientBase.CONTRAST);
        checkbox.addListener(new ActionListener() {
            @Override
            public void mouseClicked(float mouseX, float mouseY, int button) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    setState(!state);
                }
            }
        });
        checkbox.getBox().setStateSupplier(this::getState);
        return checkbox;
    }
}
