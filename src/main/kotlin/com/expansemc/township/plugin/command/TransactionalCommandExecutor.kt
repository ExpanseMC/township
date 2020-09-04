package com.expansemc.township.plugin.command

import com.expansemc.township.plugin.Township
import com.expansemc.township.plugin.storage.transaction
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandExecutor
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext

fun interface TransactionalCommandExecutor : CommandExecutor {

    fun Transaction.execute(context: CommandContext): CommandResult

    override fun execute(context: CommandContext): CommandResult =
        Township.DATABASE.transaction {
            this.execute(context)
        }
}