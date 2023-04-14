package de.dietrichpaul.clientbase.property.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.feature.command.argument.EnumArgumentType;
import de.dietrichpaul.clientbase.property.Property;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class EnumProperty<T extends Enum<T>> extends Property {

    private T value;
    private final T[] values;
    private final Class<T> clazz;

    public EnumProperty(String name, T value, T[] values, Class<T> clazz) {
        super(name);
        this.value = value;
        this.values = values;
        this.clazz = clazz;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (!ArrayUtils.contains(values, value))
            throw new IllegalArgumentException("Unknown value");
        this.value = value;
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(Arrays.binarySearch(values, value));
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element != null && element.isJsonPrimitive())
            setValue(values[element.getAsInt()]);
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                literal("set")
                        .then(argument("value", EnumArgumentType.enumField(values))
                                .executes(context -> {
                                    T value = EnumArgumentType.getEnumField(context, "value", clazz);
                                    setValue(value);
                                    ChatUtil.sendChatMessage(Text.literal(getName() + " was set to " + getValue()));
                                    return 1;
                                }))
        );
    }
}
