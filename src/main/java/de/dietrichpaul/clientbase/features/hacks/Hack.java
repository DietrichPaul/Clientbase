package de.dietrichpaul.clientbase.features.hacks;

import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.MinecraftClient;

public class Hack {

    private String name;
    private HackCategory category;
    private boolean toggled;

    protected MinecraftClient mc = MinecraftClient.getInstance();
    protected ClientBase cb = ClientBase.getInstance();

    public Hack(String name, HackCategory category) {
        this.name = name;
        this.category = category;
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public String getName() {
        return name;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        if (this.toggled == toggled)
            return;

        if (this.toggled = toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public HackCategory getCategory() {
        return category;
    }

    public void toggle() {
        setToggled(!isToggled());
    }
}
