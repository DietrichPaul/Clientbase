package de.dietrichpaul.clientbase.feature.command.list;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class HelpCommand extends Command {

    private final static int commandsPerPage = 5;

    public HelpCommand() {
        super("help");
    }

    private int list(CommandContext<CommandSource> ctx, int page) {
        CommandDispatcher<CommandSource> dispatcher = cb.getCommandList().getDispatcher();
        String[] commands = dispatcher.getSmartUsage(dispatcher.getRoot(), ctx.getSource()).values().toArray(new String[0]);
        int pages = MathHelper.ceil(commands.length / (double) commandsPerPage);


        if (page <= 0 || page > pages) {
            ChatUtil.sendChatMessage(Text.literal("Page " + page + " does not exist").formatted(Formatting.RED));
            return 1;
        }

        int pageStart = (page - 1) * commandsPerPage;
        ChatUtil.sendChatMessage(Text.literal("Usages:").formatted(Formatting.GOLD));
        for (int i = pageStart; i < Math.min(pageStart + commandsPerPage, commands.length); i++) {
            String command = commands[i];
            ChatUtil.sendChatMessage(Text.literal("• " + command));
        }
        ChatUtil.sendChatMessage(Text.literal("Page " + page + "/" + pages).formatted(Formatting.GOLD));
        return 1;
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.executes(ctx -> list(ctx, 1))
                .then(
                        argument("page", IntegerArgumentType.integer())
                                .executes(ctx -> list(ctx, IntegerArgumentType.getInteger(ctx, "page")))
                );
    }
}
