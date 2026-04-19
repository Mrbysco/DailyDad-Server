package com.mrbysco.dailydadserver;

import com.mrbysco.dailydadserver.callback.PlayerEventsCallback;
import com.mrbysco.dailydadserver.commands.FabricDadCommands;
import com.mrbysco.dailydadserver.config.JokeConfig;
import com.mrbysco.dailydadserver.handler.JokeHandler;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.world.InteractionResult;
import net.neoforged.fml.config.ModConfig;

public class DailyDadFabric implements DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {
		ConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, JokeConfig.commonSpec);

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
