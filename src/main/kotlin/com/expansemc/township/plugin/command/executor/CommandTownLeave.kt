package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOwnTown
import com.expansemc.township.plugin.command.requireSelfResident
import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.util.TextUI
import net.kyori.adventure.text.TextComponent
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext

object CommandTownLeave : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val resident: ResidentDao = context.requireSelfResident()
        val town: TownDao = context.requireOwnTown()

        if (resident.id == town.owner.id) {
            throw CommandException(TextComponent.of("You must set a new owner first, or use '/town delete'."))
        }

        resident.town = null
        context.sendMessage(
            TextComponent.of("Left the town of ").append(TextUI.townInfo(town.name))
        )
        return CommandResult.success()
    }
}