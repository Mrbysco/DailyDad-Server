package com.mrbysco.dailydadserver;

import com.mrbysco.dailydadserver.commands.ForgeDadCommands;
import com.mrbysco.dailydadserver.config.JokeConfig;
import com.mrbysco.dailydadserver.handler.JokeHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;

@Mod(Constants.MOD_ID)
public class DailyDadForge {

	public DailyDadForge() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(Type.COMMON, JokeConfig.serverSpec);
		eventBus.register(JokeConfig.class);

		CommonClass.init();

		MinecraftForge.EVENT_BUS.addListener(this::onCommandRegister);

		//Make sure the mod being absent on client side does not cause the server to be displayed as incompatible
		ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () ->
				new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY,
						(remoteVersionString, networkBool) -> true));

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			MinecraftForge.EVENT_BUS.addListener(this::onLoggedIn);
			MinecraftForge.EVENT_BUS.addListener(this::onPlayerRespawn);
		});
	}

	public void onLoggedIn(PlayerLoggedInEvent event) {
		JokeHandler.onLoggedIn(event.getEntity());
	}

	public void onPlayerRespawn(PlayerRespawnEvent event) {
		JokeHandler.onPlayerRespawn(event.getEntity(), event.isEndConquered());
	}

	public void onCommandRegister(RegisterCommandsEvent event) {
		ForgeDadCommands.initializeCommands(event.getDispatcher());
	}
}