package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.Parameters
import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOwnTown
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.util.unwrap
import net.kyori.adventure.text.TextComponent
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext

object CommandTownSetOpen : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val open: Boolean? = context.getOne(Parameters.open).unwrap()
        val town: TownDao = context.requireOwnTown()

        town.open = open ?: !town.open
        context.sendMessage(TextComponent.of("The town is now set to ${if (town.open) "open" else "closed"}."))
        return CommandResult.success()
    }
}