package de.dietrichpaul.clientbase.features.commands.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.commands.Suggestor;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

public class HackArgumentType implements ArgumentType<Hack> {

    private static final DynamicCommandExceptionType HACK_DOES_NOT_EXIST =
            new DynamicCommandExceptionType(name -> Text.of("The hack " + name + " does not exist."));

    private HackArgumentType() {
    }

    public static HackArgumentType hack() {
        return new HackArgumentType();
    }

    public static Hack getHack(CommandContext<?> ctx, String name) {
        return ctx.getArgument(name, Hack.class);
    }

    @Override
    public Hack parse(StringReader reader) throws CommandSyntaxException {
        String name = reader.readString();
        Hack hack = ClientBase.getInstance().getHackMap().getHack(name);
        if (hack == null) {
            throw HACK_DOES_NOT_EXIST.createWithContext(reader, name);
        }
        return hack;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return new Suggestor(builder)
                .addAll(ClientBase.getInstance().getHackMap().getHacks().stream().map(Hack::getName))
                .buildFuture();
    }
}
