package pigcart.glimchat.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import pigcart.glimchat.GlimChat;
import pigcart.glimchat.WebsocketClientEndpoint;
import pigcart.glimchat.config.ModConfig;

import java.net.URI;
import java.net.URISyntaxException;

public class GlimeshConnectCommand {
    public static boolean alreadyEnabled = false;
    public static LiteralArgumentBuilder<CottonClientCommandSource> getArgumentBuilder() {
        return ArgumentBuilders.literal("connect")
                .then(ArgumentBuilders.argument("channel_name", StringArgumentType.string())
                .executes(ctx -> {
                    String channelName = StringArgumentType.getString(ctx, "channel_name");

                    ModConfig config = ModConfig.getConfig();
                    config.setChannel(channelName);
                    config.save();
                    if (GlimChat.websocketClientEndpoint != null && GlimChat.websocketClientEndpoint.isOpen()) {
                        ctx.getSource().sendFeedback(new TranslatableText("text.glimchat.command.connect.already_enabled"));
                        return 1;
                        // Return a result. -1 is failure, 0 is a pass and 1 is success.
                    }
                    if (config.getOauthKey().equals("")) {
                        ctx.getSource().sendFeedback(new TranslatableText("text.glimchat.command.connect.set_config"));
                        return -1;
                    }
                    if (config.getChannel().equals("")) {
                        ctx.getSource().sendFeedback(new TranslatableText("text.glimchat.command.connect.set_channel"));
                    }
                    try {
                        // open websocket
                        System.out.println("Opening websocket");
                        GlimChat.websocketClientEndpoint = new WebsocketClientEndpoint(new URI("wss://glimesh.tv/api/socket/websocket?vsn=2.0.0&client_id=535cd97ab33c5cd159859d4a4849eb7a635f96fead877dd51004c4abb48e1d3d"));
                        GlimChat.websocketClientEndpoint.connect();
                        return 1;

                    } catch (URISyntaxException ex) {
                        System.err.println("URISyntaxException exception: " + ex.getMessage());
                        GlimChat.addGlimeshMessage("URISyntaxException exception", ex.getMessage(), Formatting.BOLD);
                        return -1;
                    }
    }));
    }
}
