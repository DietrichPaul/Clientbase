package de.dietrichpaul.clientbase.feature.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;

public abstract class Command implements Comparable<Command> {

    private String name;

    protected MinecraftClient mc = MinecraftClient.getInstance();
    protected ClientBase cb = ClientBase.INSTANCE;


    public Command(String name) {
        this.name = name;
    }

    public abstract void buildCommand(LiteralArgumentBuilder<CommandSource> root);

    /*
    wenn ich nicht faul wäre, machte ich einen LiteralArgumentBuilder,
    der in der execute methode keinen rückgabewert braucht >:(
    */
    protected static LiteralArgumentBuilder<CommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> root = literal(name);
        buildCommand(root);
        dispatcher.register(root);
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Command o) {
        return name.compareTo(o.name);
    }
}
