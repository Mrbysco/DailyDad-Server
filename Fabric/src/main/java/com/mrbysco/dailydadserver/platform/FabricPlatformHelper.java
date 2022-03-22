package com.mrbysco.dailydadserver.platform;

import com.mrbysco.dailydadserver.DailyDadFabric;
import com.mrbysco.dailydadserver.config.JokeConfig;
import com.mrbysco.dailydadserver.jokes.DadAbase;
import com.mrbysco.dailydadserver.jokes.JokeResolved;
import com.mrbysco.dailydadserver.platform.services.IPlatformHelper;
import me.shedaniel.autoconfig.AutoConfig;

import java.util.List;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public List<? extends String> getInternalDadabase() {
		if (DailyDadFabric.config == null)
			DailyDadFabric.config = AutoConfig.getConfigHolder(JokeConfig.class).getConfig();
		return DailyDadFabric.config.server.internal_dadabase;
	}

	@Override
	public boolean getJokeUponRespawn() {
		if (DailyDadFabric.config == null)
			DailyDadFabric.config = AutoConfig.getConfigHolder(JokeConfig.class).getConfig();
		return DailyDadFabric.config.server.jokeUponRespawn;
	}

	@Override
	public void getJokeAsync(JokeResolved resolved) {
		new Thread(() -> {
			String theJoke = DadAbase.getDadJoke();
			resolved.onResolve(theJoke, DadAbase.convertJokeToComponent(theJoke));
		}).start();
	}
}
