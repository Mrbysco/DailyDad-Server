package com.mrbysco.dailydadserver;

import com.mrbysco.dailydadserver.callback.PlayerEventsCallback;
import com.mrbysco.dailydadserver.commands.FabricDadCommands;
import com.mrbysco.dailydadserver.config.JokeConfig;
import com.mrbysco.dailydadserver.handler.JokeHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.world.InteractionResult;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

public class DailyDadFabric implements ModInitializer {
	private Thread watchThread = null;
	public static JokeConfig config;

	@Override
	public void onInitialize() {
		ConfigHolder<JokeConfig> holder = AutoConfig.register(JokeConfig.class, Toml4jConfigSerializer::new);
		config = holder.getConfig();
		try {
			var watchService = FileSystems.getDefault().newWatchService();
			Paths.get("config").register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			watchThread = new Thread(() -> {
				WatchKey key;
				try {
					while ((key = watchService.take()) != null) {
						if (Thread.currentThread().isInterrupted()) {
							watchService.close();
							break;
						}
						for (WatchEvent<?> event : key.pollEvents()) {
							if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
								continue;
							}
							if (((Path) event.context()).endsWith("dailydad_server.toml")) {
								Constants.LOGGER.info("Reloading Daily Dad Server config");
								if (holder.load()) {
									config = holder.getConfig();
								}
							}
						}
						key.reset();
					}
				} catch (InterruptedException ignored) {
				} catch (IOException e) {
					Constants.LOGGER.error("Failed to close filesystem watcher", e);
				}
			}, "Daily Dad Server Config Watcher");
			watchThread.start();
		} catch (IOException e) {
			Constants.LOGGER.error("Failed to create filesystem watcher for configs", e);
		}

		CommandRegistrationCallback.EVENT.register((commandDispatcher, registryAccess, environment) -> {
			if (environment == CommandSelection.DEDICATED) {
				FabricDadCommands.initializeCommands(commandDispatcher);
			}
		});

		PlayerEventsCallback.LOGIN_EVENT.register((player) -> {
			JokeHandler.onLoggedIn(player);
			return InteractionResult.PASS;
		});

		PlayerEventsCallback.RESPAWN_EVENT.register((player, endConquered) -> {
			JokeHandler.onPlayerRespawn(player, endConquered);
			return InteractionResult.PASS;
		});
	}
}
