package de.dietrichpaul.clientbase.properties.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.properties.Property;
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
        this.value = MathHelper.clamp(value, min, max);
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
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                literal("set").then(
                        argument("value", FloatArgumentType.floatArg(min, max))
                                .executes(ctx -> {
                                    setValue(FloatArgumentType.getFloat(ctx, "value"));
                                    cb.sendChatMessage(Text.literal(getName() + " was set to " + getValue()));
                                    return 1;
                                })
                )
        );
    }
}
