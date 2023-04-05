package de.dietrichpaul.clientbase.features.commands.list;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.features.commands.Command;
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
            // das könnte man in eine util verschieben
            ChatScreen chat = new ChatScreen("");
            chat.init(mc, 0, 0);
            chat.sendMessage(StringArgumentType.getString(ctx, "message"), false);
            return 1; // >:(
        }));
    }
}
