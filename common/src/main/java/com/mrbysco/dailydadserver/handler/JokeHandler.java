package com.mrbysco.dailydadserver.handler;

import com.mrbysco.dailydadserver.config.JokeConfig;
import com.mrbysco.dailydadserver.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class JokeHandler {
	public static void onLoggedIn(@Nullable ServerPlayer player) {
		if (player != null) {
			Services.PLATFORM.getJokeAsync((joke, component) ->
					player.sendSystemMessage(Component.literal("<DailyDad> ").withStyle(ChatFormatting.GOLD).append(component)));
		}
	}

	public static void onPlayerRespawn(ServerPlayer player, boolean endConquered) {
		if (JokeConfig.SERVER.jokeUponRespawn.get() && !endConquered) {
			Services.PLATFORM.getJokeAsync((joke, component) ->
					player.sendSystemMessage(Component.literal("<DailyDad> ").withStyle(ChatFormatting.GOLD).append(component)));
		}
	}
}
