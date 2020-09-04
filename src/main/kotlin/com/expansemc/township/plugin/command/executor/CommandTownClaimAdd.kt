package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOwnTown
import com.expansemc.township.plugin.storage.dao.TownClaimDao
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.util.TextUI
import com.expansemc.township.plugin.util.unwrap
import net.kyori.adventure.text.TextComponent
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.world.ServerLocation

object CommandTownClaimAdd : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val location: ServerLocation = context.cause.location.unwrap()
            ?: throw CommandException(TextComponent.of("You must be locatable to use this command."))

        val town: TownDao = context.requireOwnTown()

        val curClaim: TownClaimDao? = TownClaimDao.getClaimAt(location)
        if (curClaim != null) {
            throw CommandException(TextComponent.of("This chunk is already claimed by ${curClaim.town.name}."))
        }

        val claim: TownClaimDao = TownClaimDao.new {
            this.world = location.worldKey
            this.chunkX = location.chunkPosition.x
            this.chunkZ = location.chunkPosition.z
            this.town = town
        }

        context.sendMessage(
            TextComponent.builder("Claimed a new chunk at ")
                .append(TextUI.townClaimInfo(claim.chunkX, claim.chunkZ))
                .append(".")
                .build()
        )
        return CommandResult.success()
    }
}