package com.mrbysco.dailydadserver.mixin;

import com.mrbysco.dailydadserver.callback.PlayerEventsCallback;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public class PlayerListMixin {

	@Inject(method = "placeNewPlayer", at = @At("RETURN"))
	private void placeNewPlayer(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci) {
		PlayerEventsCallback.LOGIN_EVENT.invoker().onLogin(serverPlayer);
	}

	@Inject(method = "respawn", at = @At("RETURN"))
	private void respawn(ServerPlayer serverPlayer, boolean endConquered, CallbackInfoReturnable<ServerPlayer> cir) {
		PlayerEventsCallback.RESPAWN_EVENT.invoker().respawn(cir.getReturnValue(), endConquered);
	}
}