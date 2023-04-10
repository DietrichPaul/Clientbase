package de.dietrichpaul.clientbase.features.commands.list;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.dietrichpaul.clientbase.features.commands.Command;
import de.dietrichpaul.clientbase.features.commands.argument.KeyArgumentType;
import de.dietrichpaul.clientbase.features.commands.argument.KeyBindingArgumentType;
import de.dietrichpaul.clientbase.features.commands.suggestor.HackSuggestor;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

public class BindCommand extends Command {

    public BindCommand() {
        super("bind");
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        // was ist mit list?!
        root.then(
                literal("add").then(
                        argument("key", KeyArgumentType.key())
                                .then(argument("message", StringArgumentType.greedyString())
                                        .suggests(new HackSuggestor())
                                        .executes(this::add)))
        ).then(
                literal("remove").then(
                        argument("key", KeyBindingArgumentType.boundKey())
                                .executes(this::remove)
                )
        );
    }

    private int remove(CommandContext<CommandSource> ctx) {
        InputUtil.Key key = KeyArgumentType.getKey(ctx, "key");
        cb.getKeybindingMap().unbind(key);
        // components wären hier besser
        cb.sendChatMessage(Text.literal("Unbound everything from: " + I18n.translate(key.getTranslationKey())));
        return 1;
    }
    private int add(CommandContext<CommandSource> ctx) {
        InputUtil.Key key = KeyArgumentType.getKey(ctx, "key");
        String message = StringArgumentType.getString(ctx, "message");
        cb.getKeybindingMap().bind(key, message);
        // components wären hier besser
        cb.sendChatMessage(Text.literal("Bound: " + I18n.translate(key.getTranslationKey()) + " -> " + message));
        return 1;
    }
}
