package com.mrbysco.dailydadserver;

import com.mrbysco.dailydadserver.commands.ForgeDadCommands;
import com.mrbysco.dailydadserver.config.JokeConfig;
import com.mrbysco.dailydadserver.handler.JokeHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

@Mod(Constants.MOD_ID)
public class DailyDadNeoForge {

	public DailyDadNeoForge(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(Type.COMMON, JokeConfig.serverSpec);
		eventBus.register(JokeConfig.class);

		NeoForge.EVENT_BUS.addListener(this::onCommandRegister);
		NeoForge.EVENT_BUS.addListener(this::onLoggedIn);
		NeoForge.EVENT_BUS.addListener(this::onPlayerRespawn);
	}

	public void onLoggedIn(PlayerLoggedInEvent event) {
		JokeHandler.onLoggedIn(event.getEntity());
	}

	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		JokeHandler.onPlayerRespawn(event.getEntity(), event.isEndConquered());
	}

	public void onCommandRegister(RegisterCommandsEvent event) {
		ForgeDadCommands.initializeCommands(event.getDispatcher());
	}
}