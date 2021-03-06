package pigcart.glimchat.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.TranslatableText;
import pigcart.glimchat.GlimChat;
import pigcart.glimchat.GlimeshPlaysController;

public class GlimeshPlayCommand {
    public static LiteralArgumentBuilder<FabricClientCommandSource> getArgumentBuilder() {
        return ClientCommandManager.literal("play").executes(ctx -> {
            if (GlimChat.websocketClientEndpoint.isClosed() || GlimChat.websocketClientEndpoint==null) {
                ctx.getSource().sendFeedback(new TranslatableText("text.glimchat.command.play.no_connection"));
                return 1;
            }
            if (GlimeshPlaysController.glimeshPlays) {
                GlimeshPlaysController.glimeshPlays = false;
                ctx.getSource().sendFeedback(new TranslatableText("text.glimchat.command.play.disabled"));
            } else {
                GlimeshPlaysController.glimeshPlays = true;
                ctx.getSource().sendFeedback(new TranslatableText("text.glimchat.command.play.enabled"));
            }
            return 1;
        });
    }
}
