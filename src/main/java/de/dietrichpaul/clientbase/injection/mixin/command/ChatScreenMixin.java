package de.dietrichpaul.clientbase.injection.mixin.command;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.features.commands.CommandManager;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {

    @Shadow public abstract boolean sendMessage(String chatText, boolean addToHistory);

    public ChatScreenMixin(Text title) {
        super(title);
    }

    @Redirect(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatScreen;sendMessage(Ljava/lang/String;Z)Z"))
    public boolean onSendMessage(ChatScreen instance, String chatText, boolean addToHistory) {
        String command = chatText.trim();
        if (command.startsWith(Character.toString(CommandManager.COMMAND_PREFIX))) {
            client.inGameHud.getChatHud().addToMessageHistory(command); // stupid intellij, client != null >:(
            ClientBase.getInstance().getCommandManager().process(command);
            return true;
        }
        return sendMessage(chatText, addToHistory);
    }

}
