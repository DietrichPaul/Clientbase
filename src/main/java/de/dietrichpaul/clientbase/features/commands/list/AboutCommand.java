package de.dietrichpaul.clientbase.features.commands.list;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

public class AboutCommand extends Command {

    public AboutCommand() {
        super("about");
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.executes(ctx -> {
            cb.sendChatMessage(Text.of(ClientBase.NAME + " v" + ClientBase.VERSION));
            cb.sendChatMessage(Text.of(ClientBase.METADATA.getDescription()));
            cb.sendChatMessage(Text.of("Made by " + ClientBase.AUTHORS));
            return 1;
        });
    }
}
