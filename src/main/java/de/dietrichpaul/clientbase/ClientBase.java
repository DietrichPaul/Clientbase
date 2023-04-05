package de.dietrichpaul.clientbase;

import de.dietrichpaul.clientbase.features.FriendList;
import de.dietrichpaul.clientbase.features.commands.CommandManager;
import de.dietrichpaul.clientbase.features.hacks.HackMap;
import de.dietrichpaul.clientbase.rotation.RotationEngine;
import de.dietrichpaul.clientbase.util.render.Renderer2D;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.stream.Collectors;

public class ClientBase {

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
    private CommandManager commandManager;
    private FriendList friendList;
    private HackMap hackMap;
    private RotationEngine rotationEngine;

    // internal
    private MinecraftClient mc = MinecraftClient.getInstance();

    public static ClientBase getInstance() {
        return instance;
    }

    /*
     * pre start
     */
    public void init() {
        Renderer2D.loadShaders();
        this.commandManager = new CommandManager(); // command manager braucht die static instanz deshalb hier
        this.friendList = new FriendList();
        this.rotationEngine = new RotationEngine();
        this.hackMap = new HackMap();
    }

    public void start() {
        this.commandManager.registerBuiltInCommands();
        this.hackMap.registerBuiltInHacks();
    }

    public void stop() {

    }

    public void sendChatMessage(Text text) {
        text = Text.literal("").append(text).formatted(Formatting.GRAY); // &r -> &7
        Text line = Text.literal("").append(PREFIX).append(" ").append(text); // prefix + " " + text
        mc.inGameHud.getChatHud().addMessage(line);
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
}
