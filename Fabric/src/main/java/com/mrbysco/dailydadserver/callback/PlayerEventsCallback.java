package com.mrbysco.dailydadserver.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public interface PlayerEventsCallback {
	Event<Login> LOGIN_EVENT = EventFactory.createArrayBacked(Login.class,
			(listeners) -> (player) -> {
				for (Login event : listeners) {
					InteractionResult result = event.onLogin(player);

					if (result != InteractionResult.PASS) {
						return result;
					}
				}

				return InteractionResult.PASS;
			}
	);

	interface Login {
		InteractionResult onLogin(Player player);
	}

	Event<Respawn> RESPAWN_EVENT = EventFactory.createArrayBacked(Respawn.class,
			(listeners) -> (player, endConquered) -> {
				for (Respawn event : listeners) {
					InteractionResult result = event.respawn(player, endConquered);

					if (result != InteractionResult.PASS) {
						return result;
					}
				}

				return InteractionResult.PASS;
			}
	);

	interface Respawn {
		InteractionResult respawn(Player player, boolean endConquered);
	}
}
