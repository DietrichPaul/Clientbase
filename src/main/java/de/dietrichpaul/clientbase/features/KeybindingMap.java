package de.dietrichpaul.clientbase.features;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.KeyListener;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.florianmichael.dietrichevents.EventDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
TODO Mouse bindings
 */
public class KeybindingMap implements KeyListener {

    private final Map<InputUtil.Key, List<String>> bindings = new LinkedHashMap<>();

    public KeybindingMap() {
        EventDispatcher.g().subscribe(KeyListener.class, this);

        // remove this and save in config (default)
        bind(InputUtil.fromTranslationKey("key.keyboard.right.shift"), "ClickGui");
        bind(InputUtil.fromTranslationKey("key.keyboard.r"), "KillAura");
    }

    @Override
    public void onKey(int key, int scan, int action, int modifiers) {
        System.out.println("SDA");
        if (key != GLFW.GLFW_PRESS || MinecraftClient.getInstance().currentScreen != null) {
            return;
        }
        ChatScreen chat = new ChatScreen("");
        chat.init(MinecraftClient.getInstance(), 0, 0);
        List<String> messages = getBindings(InputUtil.fromKeyCode(key, scan));
        if (messages != null) {
            for (String message : messages) {
                Hack hack = ClientBase.getInstance().getHackMap().getHack(message);
                if (hack != null) {
                    hack.toggle();
                    continue;
                }

                chat.sendMessage(message, false); // dafür brauche ich eine util
            }
        }
    }

    public void bind(InputUtil.Key key, String message) {
        bindings.computeIfAbsent(key, k -> new LinkedList<>()).add(message);
    }

    public List<String> getBindings(InputUtil.Key key) {
        return bindings.get(key);
    }

    public void unbind(InputUtil.Key key) {
        bindings.remove(key);
    }

    public Map<InputUtil.Key, List<String>> getBindings() {
        return bindings;
    }
}
