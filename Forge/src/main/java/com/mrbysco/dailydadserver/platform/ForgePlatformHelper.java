package com.mrbysco.dailydadserver.platform;

import com.mrbysco.dailydadserver.config.JokeConfig;
import com.mrbysco.dailydadserver.jokes.DadAbase;
import com.mrbysco.dailydadserver.jokes.JokeResolved;
import com.mrbysco.dailydadserver.platform.services.IPlatformHelper;

import java.util.List;

public class ForgePlatformHelper implements IPlatformHelper {

	@Override
	public List<? extends String> getInternalDadabase() {
		return JokeConfig.SERVER.internal_dadabase.get();
	}

	@Override
	public boolean getJokeUponRespawn() {
		return JokeConfig.SERVER.jokeUponRespawn.get();
	}

	@Override
	public void getJokeAsync(JokeResolved resolved) {
		new Thread(() -> {
			String joke = DadAbase.getDadJoke();
			resolved.onResolve(joke, DadAbase.convertJokeToComponent(joke));
		}).start();
	}
}
