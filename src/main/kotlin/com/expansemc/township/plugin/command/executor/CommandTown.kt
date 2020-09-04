package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.util.TextUI
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.service.pagination.PaginationList

object CommandTown : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val pagination: PaginationList = TextUI.pagination("Town Commands") {
            add(TextUI.command("town create", "<name>"))
            add(TextUI.command("town delete"))
            add(TextUI.command("town info", "[<town>]"))
            add(TextUI.command("town join", "<town>"))
            add(TextUI.command("town leave"))
            add(TextUI.command("town list"))
            add(TextUI.command("town residents"))
            add(TextUI.command("town set"))
            add(TextUI.command("town warp"))
        }

        pagination.sendTo(context.cause.audience)
        return CommandResult.success()
    }
}