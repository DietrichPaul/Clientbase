package de.dietrichpaul.clientbase.util.minecraft;

import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChatUtil {    
    public final static MutableText PREFIX;

    static {
        PREFIX = Text.literal("").append(Text.literal("[").formatted(Formatting.DARK_GRAY)).append(Text.literal(ClientBase.NAME).formatted(Formatting.RED)).append(Text.literal("]").formatted(Formatting.DARK_GRAY));
    }

    public static void sendChatMessage(Text text) {
        text = Text.literal("").append(text).formatted(Formatting.GRAY); // &r -> &7
        Text line = Text.literal("").append(PREFIX).append(" ").append(text); // prefix + " " + text
        
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(line);
    }

    public static void sendChatMessageToServer(final String text) {
        ChatScreen chat = new ChatScreen("");
        chat.init(MinecraftClient.getInstance(), 0, 0);

        chat.sendMessage(text, false);
    }
}
