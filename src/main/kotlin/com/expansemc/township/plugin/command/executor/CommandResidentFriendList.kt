package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.Parameters
import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOne
import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.util.TextUI
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.service.pagination.PaginationList

object CommandResidentFriendList : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val resident: ResidentDao = context.requireOne(Parameters.residentOrSelf)
        val friends: SizedIterable<ResidentDao> = resident.friends

        val pagination: PaginationList = TextUI.pagination("${friends.count()} Friends of ${resident.name}") {
            friends.mapTo(destination = this) { resident: ResidentDao ->
                TextUI.residentInfo(resident.name)
            }
        }

        pagination.sendTo(context.cause.audience)
        return CommandResult.success()
    }
}