package com.mrbysco.dailydadserver.platform;

import com.mrbysco.dailydadserver.jokes.DadAbase;
import com.mrbysco.dailydadserver.jokes.JokeResolved;
import com.mrbysco.dailydadserver.platform.services.IPlatformHelper;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public void getJokeAsync(JokeResolved resolved) {
		new Thread(() -> {
			String joke = DadAbase.getDadJoke();
			resolved.onResolve(joke, DadAbase.convertJokeToComponent(joke));
		}).start();
	}
}
