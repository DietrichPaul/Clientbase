package de.dietrichpaul.clientbase.config.ext.json;

import com.google.gson.JsonArray;
import de.dietrichpaul.clientbase.config.ConfigType;
import de.dietrichpaul.clientbase.config.ext.JsonConfig;

public abstract class JsonArrayConfig extends JsonConfig<JsonArray> {

    public JsonArrayConfig(String name, ConfigType type) {
        super(name, type);
    }

    @Override
    protected JsonArray make() {
        return new JsonArray();
    }
}
