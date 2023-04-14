package de.dietrichpaul.clientbase.property;

import com.google.gson.JsonElement;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.gui.api.Component;
import net.minecraft.command.CommandSource;

public abstract class Property {

    private final String name;
    PropertyGroup parent;

    public Property(String name) {
        this.name = name;
    }

    public abstract JsonElement serialize();

    public abstract void deserialize(JsonElement element);

    protected static LiteralArgumentBuilder<CommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    public abstract void buildCommand(LiteralArgumentBuilder<CommandSource> root);

    public Component getClickGuiComponent() {
        return null;
    }

    public void reportChanges() {
        if (parent != null)
            parent.reportChanges();
    }

    public PropertyGroup getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }
}
