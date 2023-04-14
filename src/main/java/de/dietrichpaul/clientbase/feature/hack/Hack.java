package de.dietrichpaul.clientbase.feature.hack;

import com.google.gson.JsonObject;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.property.PropertyGroup;
import net.minecraft.client.MinecraftClient;

public class Hack extends PropertyGroup {

    private String name;
    private HackCategory category;
    private boolean toggled;
    private boolean saveState = true;

    protected static MinecraftClient mc = MinecraftClient.getInstance();
    protected ClientBase cb = ClientBase.INSTANCE;

    public Hack(String name, HackCategory category) {
        this.name = name;
        this.category = category;
    }

    public void doNotSaveState() {
        this.saveState = false;
    }

    @Override
    public void reportChanges() {
        cb.getConfigList().hack.save();
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
        if (saveState)
            reportChanges();
    }

    public HackCategory getCategory() {
        return category;
    }

    public void toggle() {
        setToggled(!isToggled());
    }

    public JsonObject serializeHack() {
        JsonObject object = new JsonObject();
        if (saveState) {
            object.addProperty("state", toggled);
        }
        object.add("properties", serializeProperties());
        return object;
    }

    public void deserializeHack(JsonObject object) {
        if (saveState && object.has("state")) {
            setToggled(object.get("state").getAsBoolean());
        }
        deserializeProperties(object.getAsJsonObject("properties"));
    }
}
