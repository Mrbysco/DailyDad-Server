package com.mrbysco.dailydadserver.platform.services;

import com.mrbysco.dailydadserver.jokes.JokeResolved;

public interface IPlatformHelper {

	/**
	 * Gets a joke from the online dadabase (Asynchronous)
	 *
	 * @param resolved The callback to be called when the joke is resolved
	 */
	void getJokeAsync(JokeResolved resolved);
}
