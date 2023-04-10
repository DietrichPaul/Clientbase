package de.dietrichpaul.clientbase;

import de.dietrichpaul.clientbase.config.ConfigManager;
import de.dietrichpaul.clientbase.features.FriendList;
import de.dietrichpaul.clientbase.features.KeybindingMap;
import de.dietrichpaul.clientbase.features.commands.CommandManager;
import de.dietrichpaul.clientbase.features.gui.api.font.FontAtlas;
import de.dietrichpaul.clientbase.features.hacks.HackMap;
import de.dietrichpaul.clientbase.features.rotation.RotationEngine;
import de.dietrichpaul.clientbase.util.render.Renderer2D;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

public class ClientBase implements SimpleSynchronousResourceReloadListener {

    public static final int CONTRAST = 0xfff76b00; // pls remove this
    private static final ClientBase instance = new ClientBase();

    // meta
    public static final ModMetadata METADATA = FabricLoader.getInstance()
            .getModContainer("clientbase").orElseThrow().getMetadata();
    public static final String NAME = METADATA.getName();
    public static final String VERSION = METADATA.getVersion().getFriendlyString();
    public static final String AUTHORS = METADATA.getAuthors().stream().map(Person::getName)
            .collect(Collectors.joining(", "));
    public static final MutableText PREFIX = Text.literal("")
            .append(Text.literal("[").formatted(Formatting.DARK_GRAY))
            .append(Text.literal(NAME).formatted(Formatting.RED))
            .append(Text.literal("]").formatted(Formatting.DARK_GRAY));

    // features
    private ConfigManager configManager;
    private CommandManager commandManager;
    private FriendList friendList;
    private FontAtlas verdana;
    private HackMap hackMap;
    private KeybindingMap keybindingMap;
    private RotationEngine rotationEngine;

    // internal
    private File directory;
    private MinecraftClient mc = MinecraftClient.getInstance();
    private double prevGLTime = Double.NaN;
    private double fps;

    public static ClientBase getInstance() {
        return instance;
    }

    /*
     * pre start
     */
    public void init() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(this);
        Renderer2D.loadShaders();
        this.commandManager = new CommandManager(); // command manager braucht die static instanz, deshalb hier
        this.friendList = new FriendList();
        this.rotationEngine = new RotationEngine();
        this.hackMap = new HackMap();
        this.keybindingMap = new KeybindingMap();
        loadIO();
        this.configManager = new ConfigManager();
    }

    private void loadIO() {
        this.directory = new File(mc.runDirectory, NAME);
        //noinspection ResultOfMethodCallIgnored
        this.directory.mkdir();
    }

    private void loadFonts(ResourceManager manager) {
        try {
            verdana = new FontAtlas(manager, "verdana");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load fonts", e);
        }
    }

    public KeybindingMap getKeybindingMap() {
        return keybindingMap;
    }

    public File getDirectory() {
        return directory;
    }

    public void start() {
        this.hackMap.registerBuiltInHacks();
        this.commandManager.registerBuiltInCommands();
        this.configManager.start();
    }

    public void stop() {
    }

    public void sendChatMessage(Text text) {
        text = Text.literal("").append(text).formatted(Formatting.GRAY); // &r -> &7
        Text line = Text.literal("").append(PREFIX).append(" ").append(text); // prefix + " " + text
        mc.inGameHud.getChatHud().addMessage(line);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public FriendList getFriendList() {
        return this.friendList;
    }

    public RotationEngine getRotationEngine() {
        return rotationEngine;
    }

    public HackMap getHackMap() {
        return this.hackMap;
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("dyson:staubsauger");
    }

    @Override
    public void reload(ResourceManager manager) {
        loadFonts(manager);
    }

    public FontAtlas getVerdana() {
        return verdana;
    }

    public void drawFrame() {
        if (Double.isNaN(prevGLTime)) {
            prevGLTime = GLFW.glfwGetTime();
            return;
        }
        double time = GLFW.glfwGetTime();
        double delta = time - prevGLTime;
        fps = 1.0 / delta;
        prevGLTime = time;
    }

    public double getFps() {
        return fps;
    }
}