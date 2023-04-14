package de.dietrichpaul.clientbase.feature.command.list;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.feature.command.argument.KeyArgumentType;
import de.dietrichpaul.clientbase.feature.command.argument.KeyBindingArgumentType;
import de.dietrichpaul.clientbase.feature.command.suggestor.HackSuggestor;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
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
        cb.getKeybindingList().unbind(key);
        ChatUtil.sendChatMessage(Text.literal("Unbound everything from: " + I18n.translate(key.getTranslationKey())));
        return 1;
    }
    private int add(CommandContext<CommandSource> ctx) {
        InputUtil.Key key = KeyArgumentType.getKey(ctx, "key");
        String message = StringArgumentType.getString(ctx, "message");
        cb.getKeybindingList().bind(key, message);
        ChatUtil.sendChatMessage(Text.literal("Bound: " + I18n.translate(key.getTranslationKey()) + " -> " + message));
        return 1;
    }
}
