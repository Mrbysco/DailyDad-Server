package com.mrbysco.dailydadserver.platform;

import com.mrbysco.dailydadserver.jokes.DadAbase;
import com.mrbysco.dailydadserver.jokes.JokeResolved;
import com.mrbysco.dailydadserver.platform.services.IPlatformHelper;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public void getJokeAsync(JokeResolved resolved) {
		new Thread(() -> {
			String theJoke = DadAbase.getDadJoke();
			resolved.onResolve(theJoke, DadAbase.convertJokeToComponent(theJoke));
		}).start();
	}
}
