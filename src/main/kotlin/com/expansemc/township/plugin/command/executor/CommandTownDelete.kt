package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOwnTown
import com.expansemc.township.plugin.command.requireSelfResident
import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.storage.dao.TownDao
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext

object CommandTownDelete : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val resident: ResidentDao = context.requireSelfResident()
        val town: TownDao = context.requireOwnTown()

        if (resident.citizen != town.owner) {
            throw CommandException(TextComponent.of("Only the town owner can delete the town."))
        }

        val message: TextComponent = TextComponent.builder("The town of ", NamedTextColor.GRAY)
            .append(TextComponent.of(town.name, NamedTextColor.RED))
            .append(" has fallen.")
            .build()

        town.delete()

        Sponge.getServer().sendMessage(message)
        return CommandResult.success()
    }
}