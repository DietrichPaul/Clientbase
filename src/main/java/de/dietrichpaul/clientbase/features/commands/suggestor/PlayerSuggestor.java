package de.dietrichpaul.clientbase.features.commands.suggestor;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.dietrichpaul.clientbase.features.commands.Suggestor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class PlayerSuggestor implements SuggestionProvider<CommandSource> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        MinecraftClient mc = MinecraftClient.getInstance();
        return new Suggestor(builder)
                .addAll(Objects.requireNonNull(mc.getNetworkHandler()).getPlayerList().stream()
                        .filter(p -> !p.getProfile().getId().equals(Objects.requireNonNull(mc.player).getUuid()))
                        .map(p -> p.getProfile().getName()))
                .buildFuture();
    }
}
