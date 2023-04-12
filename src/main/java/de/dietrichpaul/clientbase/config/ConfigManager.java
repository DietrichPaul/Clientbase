package de.dietrichpaul.clientbase.config;

import de.dietrichpaul.clientbase.config.list.HackConfig;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.ClientBase;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

public class ConfigManager implements UpdateListener {
    private final Set<AbstractConfig> configs = new LinkedHashSet<>();

    public HackConfig hack = new HackConfig();

    public ConfigManager() {
        for (Field field : getClass().getFields()) {
            if (AbstractConfig.class.isAssignableFrom(field.getType())) {
                try {
                    addConfig((AbstractConfig) field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addConfig(AbstractConfig config) {
        this.configs.add(config);
    }

    public void start() {
        ClientBase.getInstance().getEventDispatcher().subscribe(UpdateListener.class, this);

        for (AbstractConfig config : this.configs) {
            if (config.getType() == ConfigType.PRE) {
                try {
                    config.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onUpdate() {
        for (AbstractConfig config : this.configs) {
            try {
                if (config.getType() == ConfigType.IN_GAME)
                    config.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ClientBase.getInstance().getEventDispatcher().unsubscribe(UpdateListener.class, this);
    }
}
