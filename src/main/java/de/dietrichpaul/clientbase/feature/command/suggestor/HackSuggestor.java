package de.dietrichpaul.clientbase.feature.command.suggestor;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import net.minecraft.command.CommandSource;

import java.util.concurrent.CompletableFuture;

public class HackSuggestor implements SuggestionProvider<CommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        return new Suggestor(builder)
                .addAll(ClientBase.INSTANCE.getHackList().getHacks().stream().map(Hack::getName))
                .buildFuture();
    }
}
