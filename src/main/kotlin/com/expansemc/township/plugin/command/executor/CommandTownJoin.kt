package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.Parameters
import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOne
import com.expansemc.township.plugin.command.requireSelfResident
import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.storage.dao.TownCitizenDao
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.util.TextUI
import net.kyori.adventure.text.TextComponent
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext

object CommandTownJoin : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val resident: ResidentDao = context.requireSelfResident()

        if (resident.citizen != null) {
            throw CommandException(TextComponent.of("You must leave your current town first."))
        }

        val town: TownDao = context.requireOne(Parameters.town)

        if (!town.open) {
            throw CommandException(TextComponent.of("That town is not open to new members."))
        }

        val citizen: TownCitizenDao = TownCitizenDao.new {
            this.town = town
            this.resident = resident
        }

        context.sendMessage(TextComponent.of("Joined the town of ").append(TextUI.townInfo(town.name)))
        return CommandResult.success()
    }
}