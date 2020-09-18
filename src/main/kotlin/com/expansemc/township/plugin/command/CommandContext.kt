package com.expansemc.township.plugin.command

import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.storage.dao.TownClaimDao
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.util.unwrap
import net.kyori.adventure.text.TextComponent
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.entity.living.player.server.ServerPlayer
import org.spongepowered.api.world.ServerLocation

fun CommandContext.getSelfResident(): ResidentDao? =
    this.cause.first(ServerPlayer::class.java).unwrap()?.let { ResidentDao.findByPlayerId(it.uniqueId) }

fun CommandContext.requireSelfResident(): ResidentDao =
    this.getSelfResident()
        ?: throw CommandException(TextComponent.of("You must be a resident to do that."))

fun CommandContext.getOwnTown(): TownDao? =
    this.getSelfResident()?.citizen?.town

fun CommandContext.requireOwnTown(): TownDao =
    this.getOwnTown()
        ?: throw CommandException(TextComponent.of("You must be in a town to do that."))

fun CommandContext.requireInClaim(town: TownDao?): TownClaimDao {
    val location: ServerLocation = this.cause.location.unwrap()
        ?: throw CommandException(TextComponent.of("You must be locatable to use this command."))
    val claim: TownClaimDao = TownClaimDao.getClaimAt(location)
        ?: throw CommandException(TextComponent.of("You must be within a town claim to do that."))
    if (town != null && claim.town != town) {
        throw CommandException(TextComponent.of("You must be within a town claim of ${town.name} to do that."))
    }
    return claim
}