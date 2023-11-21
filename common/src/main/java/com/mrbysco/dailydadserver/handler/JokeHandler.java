package com.mrbysco.dailydadserver.handler;

import com.mrbysco.dailydadserver.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class JokeHandler {
	public static void onLoggedIn(@Nullable Player player) {
		if (player != null) {
			Services.PLATFORM.getJokeAsync((joke, component) ->
					player.sendSystemMessage(Component.literal("<DailyDad> ").withStyle(ChatFormatting.GOLD).append(component)));
		}
	}

	public static void onPlayerRespawn(Player player, boolean endConquered) {
		if (Services.PLATFORM.getJokeUponRespawn() && !endConquered) {
			Services.PLATFORM.getJokeAsync((joke, component) ->
					player.sendSystemMessage(Component.literal("<DailyDad> ").withStyle(ChatFormatting.GOLD).append(component)));
		}
	}
}
