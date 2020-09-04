package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireInClaim
import com.expansemc.township.plugin.command.requireOwnTown
import com.expansemc.township.plugin.storage.dao.TownClaimDao
import com.expansemc.township.plugin.storage.dao.TownDao
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext

object CommandTownClaimRemove : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val town: TownDao = context.requireOwnTown()
        val claim: TownClaimDao = context.requireInClaim(town)

        claim.delete()

        return CommandResult.success()
    }
}