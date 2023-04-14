package de.dietrichpaul.clientbase.properties.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.properties.Property;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class IntProperty extends Property {

    private int value;
    private int min;
    private int max;

    public IntProperty(String name, int value, int min, int max) {
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
            value = element.getAsInt();
    }

    public void setValue(int value) {
        this.value = MathHelper.clamp(value, min, max);
        reportChanges();
    }

    public int getValue() {
        return value;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                literal("set").then(
                        argument("value", IntegerArgumentType.integer(min, max))
                                .executes(ctx -> {
                                    setValue(IntegerArgumentType.getInteger(ctx, "value"));
                                    cb.sendChatMessage(Text.literal(getName() + " was set to " + getValue()));
                                    return 1;
                                })
                )
        );
    }
}
