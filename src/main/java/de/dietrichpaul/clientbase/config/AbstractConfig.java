package de.dietrichpaul.clientbase.config;

import de.dietrichpaul.clientbase.ClientBase;

import java.io.File;
import java.io.IOException;

public abstract class AbstractConfig {

    protected ClientBase cb = ClientBase.INSTANCE;

    private final String name;
    private final File file;
    private boolean loaded;
    private final ConfigType type;

    public AbstractConfig(String name, ConfigType type) {
        this.name = name;
        this.type = type;
        this.file = new File(cb.getDirectory(), this.name);
    }

    public ConfigType getType() {
        return type;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public void load() throws IOException {
        read();
        save();
    }

    public void save() {
        if (!isLoaded())
            return;
        try {
            if (this.file.getParentFile() != null) {
                this.file.getParentFile().mkdirs();
            }
            this.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void markAsLoaded() {
        this.loaded = true;
    }

    protected abstract void read() throws IOException;
    protected abstract void write() throws IOException;
}
