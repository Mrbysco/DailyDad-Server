package com.mrbysco.dailydadserver.config;

import com.mrbysco.dailydadserver.Constants;
import com.mrbysco.dailydadserver.DailyDadFabric;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.List;

@Config(name = Constants.MOD_ID)
public class JokeConfig implements ConfigData {

	@CollapsibleObject
	public Server server = new Server();

	public static class Server {
		@Comment("The internal dad-abase of jokes for in case the mod is unable to reach the API")
		public List<String> internal_dadabase = List.of(DailyDadFabric.dadabase);

		@Comment("Should a joke be told upon death [default: false]")
		public boolean jokeUponRespawn = false;
	}
}