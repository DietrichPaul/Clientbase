package de.dietrichpaul.clientbase.features.commands.list;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.dietrichpaul.clientbase.features.commands.Command;
import de.dietrichpaul.clientbase.features.commands.argument.FriendArgumentType;
import de.dietrichpaul.clientbase.features.commands.suggestor.PlayerSuggestor;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class FriendCommand extends Command {

    private static final int friendsPerPage = 5;

    public FriendCommand() {
        super("friend");
    }

    private int list(CommandContext<CommandSource> ctx, int page) {
        String[] friends = cb.getFriendList().getFriends().toArray(new String[0]);
        if (friends.length == 0) {
            cb.sendChatMessage(Text.literal("You don't have any friends.").formatted(Formatting.RED));
            return 1;
        }
        int pages = MathHelper.ceil(friends.length / (double) friendsPerPage);


        if (page <= 0 || page > pages) {
            cb.sendChatMessage(Text.literal("Page " + page + " does not exist.").formatted(Formatting.RED));
            return 1;
        }

        int pageStart = (page - 1) * friendsPerPage;
        cb.sendChatMessage(Text.literal("Friends:").formatted(Formatting.GOLD));
        for (int i = pageStart; i < Math.min(pageStart + friendsPerPage, friends.length); i++) {
            String friend = friends[i];
            cb.sendChatMessage(Text.literal("• " + friend));
        }
        cb.sendChatMessage(Text.literal("Page " + page + "/" + pages).formatted(Formatting.GOLD));
        return 1;
    }

    private int add(CommandContext<CommandSource> ctx) {
        String friend = StringArgumentType.getString(ctx, "name");
        cb.getFriendList().add(friend);
        cb.sendChatMessage(Text.of("You have made a friendship with " + friend + "."));
        return 1;
    }

    private int remove(CommandContext<CommandSource> ctx) {
        String friend = FriendArgumentType.getFriend(ctx, "name");
        cb.getFriendList().remove(friend);
        cb.sendChatMessage(Text.of("You have ended the friendship with " + friend + "."));
        return 1;
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root
                .executes(ctx -> list(ctx, 1))
                .then(literal("list")
                        .executes(ctx -> list(ctx, 1))
                        .then(argument("page", IntegerArgumentType.integer())
                                .executes(ctx -> list(ctx, IntegerArgumentType.getInteger(ctx, "page")))))
                .then(literal("add")
                        .then(argument("name", StringArgumentType.word())
                                .suggests(new PlayerSuggestor())
                                .executes(this::add)))
                .then(literal("remove")
                        .then(argument("name", FriendArgumentType.friend())
                                .executes(this::remove)))
        ;
    }
}
