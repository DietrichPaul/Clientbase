package de.dietrichpaul.clientbase.util.io;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IOUtil {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @SuppressWarnings("unchecked")
    public static <E extends JsonElement> E readGsonOr(File file, E fallback) {
        try {
            return (E) JsonParser.parseReader(new FileReader(file));
        } catch (Exception ignored) {
        }
        return fallback;
    }

    public static void writePrettyGson(File file, JsonElement elem) {
        try {
            Files.asCharSink(file, StandardCharsets.UTF_8).write(GSON.toJson(elem));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
