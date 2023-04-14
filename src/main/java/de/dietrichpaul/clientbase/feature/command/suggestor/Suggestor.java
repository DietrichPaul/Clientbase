package de.dietrichpaul.clientbase.feature.command.suggestor;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Suggestor {
    private final SuggestionsBuilder builder;

    public Suggestor(SuggestionsBuilder builder) {
        this.builder = builder;
    }

    public Suggestor add(String str) {
        if (str.toLowerCase().startsWith(builder.getRemainingLowerCase()))
            builder.suggest(str);
        return this;
    }

    public Suggestor addAll(Iterable<String> list) {
        list.forEach(this::add);
        return this;
    }

    public Suggestor addAll(Stream<String> stream) {
        stream.forEach(this::add);
        return this;
    }

    public CompletableFuture<Suggestions> buildFuture() {
        return builder.buildFuture();
    }
}
