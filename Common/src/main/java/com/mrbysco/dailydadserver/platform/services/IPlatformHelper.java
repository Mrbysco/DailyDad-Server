package com.mrbysco.dailydadserver.platform.services;

import com.mrbysco.dailydadserver.jokes.JokeResolved;

import java.util.List;

public interface IPlatformHelper {

	/**
	 * Gets the internal database of jokes for in case no connection is available.
	 *
	 * @return A list of jokes from the config
	 */
	List<? extends String> getInternalDadabase();

	/**
	 * Should a joke be told upon death?
	 *
	 * @return the jokeUponRespawn config option
	 */
	boolean getJokeUponRespawn();

	/**
	 * Get's a joke from the online dadabase (Asynchronous)
	 *
	 * @param resolved
	 */
	void getJokeAsync(JokeResolved resolved);
}
