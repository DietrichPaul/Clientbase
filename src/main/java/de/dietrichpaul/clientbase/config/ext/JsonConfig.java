package de.dietrichpaul.clientbase.config.ext;

import com.google.gson.JsonElement;
import de.dietrichpaul.clientbase.config.AbstractConfig;
import de.dietrichpaul.clientbase.config.ConfigType;
import de.dietrichpaul.clientbase.util.jvm.IOUtil;

public abstract class JsonConfig<T extends JsonElement> extends AbstractConfig {

    public JsonConfig(String name, ConfigType type) {
        super(name + ".json", type);
    }

    @Override
    protected void read() {
        this.read(IOUtil.readGsonOr(getFile(), make()));
        this.markAsLoaded();
    }

    @Override
    protected void write() {
        T elem = make();
        write(elem);
        IOUtil.writePrettyGson(getFile(), elem);
    }

    protected abstract T make();

    protected abstract void read(T element);
    protected abstract void write(T element);
}
