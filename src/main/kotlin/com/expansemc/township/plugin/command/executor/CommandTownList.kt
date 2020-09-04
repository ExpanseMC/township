package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.storage.table.TownTable
import com.expansemc.township.plugin.util.TextUI
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.service.pagination.PaginationList

object CommandTownList : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val towns: SizedIterable<TownDao> = TownDao.all()
            .orderBy(TownTable.name to SortOrder.ASC)

        val pagination: PaginationList = TextUI.pagination("${towns.count()} Towns") {
            towns.mapTo(destination = this) { town: TownDao ->
                TextUI.townInfo(town.name)
            }
        }

        pagination.sendTo(context.cause.audience)

        return CommandResult.success()
    }
}