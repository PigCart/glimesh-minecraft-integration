package pigcart.glimchat;

import java.util.UUID;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import pigcart.glimchat.commands.GlimeshBaseCommand;
import pigcart.glimchat.config.ModConfig;

public class GlimChat implements ModInitializer {
    public static WebsocketClientEndpoint websocketClientEndpoint;
    @Override
    public void onInitialize() {
        ModConfig.getConfig().load();

        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
            GlimeshBaseCommand.registerCommands();
        }));
    }

    public static void addGlimeshMessage(String username, String message, Formatting textColor) {
        MutableText usernameText = new LiteralText(username).formatted(Formatting.BLUE).append(": ");
        MutableText messageBodyText = new LiteralText(message).formatted(textColor);
        MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, usernameText.append(messageBodyText), UUID.randomUUID());
    }
    public static void addNotification(MutableText message) {
        MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, message, UUID.randomUUID());
    }

}
