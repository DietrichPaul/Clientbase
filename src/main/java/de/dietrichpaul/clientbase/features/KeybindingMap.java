package de.dietrichpaul.clientbase.features;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.mojang.brigadier.arguments.StringArgumentType;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.KeyEvent;
import de.dietrichpaul.clientbase.features.hacks.Hack;
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
public class KeybindingMap {

    private final Map<InputUtil.Key, List<String>> bindings = new LinkedHashMap<>();

    public KeybindingMap() {
        EventManager.register(this);

        // remove this and save in config (default)
        bind(InputUtil.fromTranslationKey("key.keyboard.right.shift"), "ClickGui");
        bind(InputUtil.fromTranslationKey("key.keyboard.r"), "KillAura");
    }

    @EventTarget
    public void onKey(KeyEvent event) {
        if (event.getAction() != GLFW.GLFW_PRESS || MinecraftClient.getInstance().currentScreen != null) {
            return;
        }
        ChatScreen chat = new ChatScreen("");
        chat.init(MinecraftClient.getInstance(), 0, 0);
        List<String> messages = getBindings(InputUtil.fromKeyCode(event.getKey(), event.getScan()));
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
