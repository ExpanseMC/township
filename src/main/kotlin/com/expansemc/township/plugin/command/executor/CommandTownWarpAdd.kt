package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.Parameters
import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireInClaim
import com.expansemc.township.plugin.command.requireOwnTown
import com.expansemc.township.plugin.storage.dao.TownClaimDao
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.storage.dao.TownWarpDao
import com.expansemc.township.plugin.util.TextUI
import com.expansemc.township.plugin.util.unwrap
import net.kyori.adventure.text.TextComponent
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.world.ServerLocation

object CommandTownWarpAdd : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val location: ServerLocation = context.cause.location.unwrap()
            ?: throw CommandException(TextComponent.of("You must be locatable to use this command."))
        val name: String = context.requireOne(Parameters.name)

        val town: TownDao = context.requireOwnTown()
        val claim: TownClaimDao = context.requireInClaim(town)

        val warp: TownWarpDao = TownWarpDao.new {
            this.town = town
            this.claim = claim
            this.name = name
            this.location = location
        }

        context.sendMessage(
            TextComponent.builder("Created a warp named ")
                .append(TextUI.townWarpInfo(warp.name))
                .append(" at your location.")
                .build()
        )
        return CommandResult.success()
    }
}