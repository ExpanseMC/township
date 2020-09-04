package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.storage.table.ResidentTable
import com.expansemc.township.plugin.util.TextUI
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.service.pagination.PaginationList

object CommandResidentList : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val residents: SizedIterable<ResidentDao> = ResidentDao.all()
            .orderBy(ResidentTable.name to SortOrder.ASC)

        val pagination: PaginationList = TextUI.pagination("${residents.count()} Residents") {
            residents.mapTo(destination = this) { resident: ResidentDao ->
                TextUI.residentInfo(resident.name)
            }
        }

        pagination.sendTo(context.cause.audience)
        return CommandResult.success()
    }
}