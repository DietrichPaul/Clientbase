package de.dietrichpaul.clientbase.config.ext.json;

import com.google.gson.JsonObject;
import de.dietrichpaul.clientbase.config.ConfigType;
import de.dietrichpaul.clientbase.config.ext.JsonConfig;

public abstract class JsonObjectConfig extends JsonConfig<JsonObject> {

    public JsonObjectConfig(String name, ConfigType type) {
        super(name, type);
    }

    @Override
    protected JsonObject make() {
        return new JsonObject();
    }
}
