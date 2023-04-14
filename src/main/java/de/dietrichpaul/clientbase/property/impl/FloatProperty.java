package de.dietrichpaul.clientbase.property.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.feature.gui.api.Component;
import de.dietrichpaul.clientbase.feature.gui.api.Label;
import de.dietrichpaul.clientbase.feature.gui.api.Slider;
import de.dietrichpaul.clientbase.property.Property;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class FloatProperty extends Property {

    private float value;
    private float min;
    private float max;

    public FloatProperty(String name, float value, float min, float max) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(value);
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element != null)
            value = element.getAsFloat();
    }

    public void setValue(float value) {
        this.value = MathHelper.clamp((Math.round(value * 10)) / 10F, min, max);
        reportChanges();
    }

    public float getValue() {
        return value;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    @Override
    public Component getClickGuiComponent() {
        return new Slider(new Label(Text.of(getName())), this::getValue, this::getMin, this::getMax, this::setValue);
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                literal("set").then(
                        argument("value", FloatArgumentType.floatArg(min, max))
                                .executes(ctx -> {
                                    setValue(FloatArgumentType.getFloat(ctx, "value"));
                                    ChatUtil.sendChatMessage(Text.literal(getName() + " was set to " + getValue()));
                                    return 1;
                                })
                )
        );
    }
}
