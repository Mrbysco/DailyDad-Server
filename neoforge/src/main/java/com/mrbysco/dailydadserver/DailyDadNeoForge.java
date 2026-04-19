package com.mrbysco.dailydadserver;

import com.mrbysco.dailydadserver.commands.NeoForgeDadCommands;
import com.mrbysco.dailydadserver.config.JokeConfig;
import com.mrbysco.dailydadserver.handler.JokeHandler;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

@Mod(Constants.MOD_ID)
public class DailyDadNeoForge {

	public DailyDadNeoForge(ModContainer container) {
		container.registerConfig(Type.COMMON, JokeConfig.commonSpec);

		NeoForge.EVENT_BUS.addListener(this::onCommandRegister);
		NeoForge.EVENT_BUS.addListener(this::onLoggedIn);
		NeoForge.EVENT_BUS.addListener(this::onPlayerRespawn);
	}

	public void onLoggedIn(PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer)
			JokeHandler.onLoggedIn(serverPlayer);
	}

	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer)
			JokeHandler.onPlayerRespawn(serverPlayer, event.isEndConquered());
	}

	public void onCommandRegister(RegisterCommandsEvent event) {
		NeoForgeDadCommands.initializeCommands(event.getDispatcher());
	}
}