package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.Parameters
import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOne
import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.util.TextUI
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.service.pagination.PaginationList

object CommandResidentInfo : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val resident: ResidentDao = context.requireOne(Parameters.residentOrSelf)

        val pagination: PaginationList = TextUI.pagination("Resident: ${resident.name}") {
            add(TextUI.property("Id", resident.id.value.toString()))
            add(TextUI.property("Name", resident.name))

            when (val town: TownDao? = resident.town) {
                null -> add(TextUI.property("Town", "<none>"))
                else -> add(
                    TextUI.clickableProperty(
                        name = "Town",
                        value = "[${town.name}]",
                        hover = "Click here to see town information.",
                        command = "/township:town info ${town.name}"
                    )
                )
            }
        }

        pagination.sendTo(context.cause.audience)
        return CommandResult.success()
    }
}