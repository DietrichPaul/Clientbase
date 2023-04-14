package de.dietrichpaul.clientbase.feature.command.list;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.command.CommandSource;

/*
Das kann möglicherweise detected werden: TabCompletion
 */
public class SayCommand extends Command {

    public SayCommand() {
        super("say");
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(argument("message", StringArgumentType.greedyString()).executes(ctx -> {
            ChatUtil.sendChatMessageToServer(StringArgumentType.getString(ctx, "message"));
            return 1;
        }));
    }
}
