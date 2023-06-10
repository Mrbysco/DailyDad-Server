package com.mrbysco.dailydadserver.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mrbysco.dailydadserver.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ForgeDadCommands {
	public static void initializeCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
		final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("dailydad");
		root.requires((sourceStack) -> sourceStack.hasPermission(2))
				.then(Commands.literal("joke").executes(ForgeDadCommands::sendJoke));
		dispatcher.register(root);
	}

	private static int sendJoke(CommandContext<CommandSourceStack> ctx) {
		Services.PLATFORM.getJokeAsync((joke, component) -> {
			ctx.getSource().sendSuccess(() -> Component.literal("<DailyDad> ").withStyle(ChatFormatting.GOLD).append(component), false);
		});
		return 0;
	}
}
