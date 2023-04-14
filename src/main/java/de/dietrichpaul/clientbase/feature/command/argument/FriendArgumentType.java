package de.dietrichpaul.clientbase.feature.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.command.suggestor.Suggestor;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

public class FriendArgumentType implements ArgumentType<String> {
    private final static DynamicCommandExceptionType NOT_FRIENDS = new DynamicCommandExceptionType(name -> Text.literal("You are not friends with " + name + "."));

    public static FriendArgumentType friend() {
        return new FriendArgumentType();
    }

    public static String getFriend(CommandContext<?> ctx, String name) {
        return ctx.getArgument(name, String.class);
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String name = reader.readString();
        if (!ClientBase.INSTANCE.getFriendList().getFriends().contains(name))
            throw NOT_FRIENDS.createWithContext(reader, name);
        return name;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return new Suggestor(builder).addAll(ClientBase.INSTANCE.getFriendList().getFriends()).buildFuture();
    }
}
