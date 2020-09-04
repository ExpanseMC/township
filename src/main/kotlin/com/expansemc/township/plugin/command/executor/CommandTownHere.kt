package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireInClaim
import com.expansemc.township.plugin.storage.dao.TownClaimDao
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.service.pagination.PaginationList

object CommandTownHere : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val claim: TownClaimDao = context.requireInClaim(null)

        val pagination: PaginationList = CommandTownInfo.getPagination(claim.town)

        pagination.sendTo(context.cause.audience)
        return CommandResult.success()
    }
}