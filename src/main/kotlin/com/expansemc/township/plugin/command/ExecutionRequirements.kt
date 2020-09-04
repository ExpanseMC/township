package com.expansemc.township.plugin.command

import org.spongepowered.api.command.CommandCause
import org.spongepowered.api.entity.Entity
import org.spongepowered.api.entity.living.player.server.ServerPlayer

object ExecutionRequirements {
    fun isEntity(cause: CommandCause): Boolean =
        cause.first(Entity::class.java).isPresent

    fun isPlayer(cause: CommandCause): Boolean =
        cause.first(ServerPlayer::class.java).isPresent

    fun hasLocation(cause: CommandCause): Boolean =
        cause.location.isPresent
}