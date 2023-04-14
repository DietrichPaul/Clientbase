package de.dietrichpaul.clientbase;

import de.dietrichpaul.clientbase.config.ConfigList;
import de.dietrichpaul.clientbase.feature.FontList;
import de.dietrichpaul.clientbase.feature.FriendList;
import de.dietrichpaul.clientbase.feature.KeybindingList;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickEngine;
import de.dietrichpaul.clientbase.feature.command.CommandList;
import de.dietrichpaul.clientbase.feature.hack.HackList;
import de.dietrichpaul.clientbase.feature.engine.rotation.RotationEngine;
import de.dietrichpaul.clientbase.util.render.api.Renderer2D;
import de.florianmichael.dietrichevents.EventDispatcher;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.util.stream.Collectors;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ClientBase implements ClientModInitializer {
    public final static ModMetadata METADATA = FabricLoader.getInstance().getModContainer("clientbase").orElseThrow().getMetadata();

    public final static String NAME = METADATA.getName();
    public final static String VERSION = METADATA.getVersion().getFriendlyString();
    public final static String AUTHORS = METADATA.getAuthors().stream().map(Person::getName).collect(Collectors.joining(", "));

    public final static ClientBase INSTANCE = new ClientBase();

    private final EventDispatcher eventDispatcher = EventDispatcher.create(key -> new Object2ObjectArrayMap<>());
    private final File directory = new File(MinecraftClient.getInstance().runDirectory, NAME);

    private ClickEngine clickEngine;
    private RotationEngine rotationEngine;

    private FontList fontList;
    private CommandList commandList;
    private FriendList friendList;
    private HackList hackList;
    private KeybindingList keybindingList;
    private ConfigList configList;

    // Pre Init - Before Minecraft
    @Override
    public void onInitializeClient() {
        this.directory.mkdir();
        Renderer2D.loadShaders();

        this.clickEngine = new ClickEngine();
        this.rotationEngine = new RotationEngine();

        this.fontList = new FontList();
        this.commandList = new CommandList();
        this.friendList = new FriendList();
        this.hackList = new HackList();
        this.keybindingList = new KeybindingList();
        this.configList = new ConfigList();
    }

    // Post Init - After Minecraft
    public void init() {
        this.hackList.registerBuiltInHacks();
        this.commandList.registerBuiltInCommands();
        this.configList.start();
    }

    public void stop() {
        // Not implemented atm
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public File getDirectory() {
        return directory;
    }

    public ClickEngine getClickEngine() {
        return clickEngine;
    }

    public RotationEngine getRotationEngine() {
        return rotationEngine;
    }

    public FontList getFontList() {
        return fontList;
    }

    public CommandList getCommandList() {
        return commandList;
    }

    public FriendList getFriendList() {
        return friendList;
    }

    public HackList getHackList() {
        return hackList;
    }

    public KeybindingList getKeybindingList() {
        return keybindingList;
    }

    public ConfigList getConfigList() {
        return configList;
    }
}
