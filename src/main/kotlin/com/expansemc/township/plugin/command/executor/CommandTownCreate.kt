package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.Parameters
import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.storage.table.TownTable
import com.expansemc.township.plugin.util.TextUI
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.entity.living.player.server.ServerPlayer

object CommandTownCreate : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val player: ServerPlayer = context.cause.first(ServerPlayer::class.java).get()

        val name: String = context.requireOne(Parameters.name)

        val resident: ResidentDao = ResidentDao.findByPlayerId(player.uniqueId)
            ?: throw CommandException(TextComponent.of("Must be a resident to use this command!"))

        if (resident.town != null) {
            throw CommandException(TextComponent.of("You must leave your current town first."))
        }

        if (!TownDao.find { TownTable.name eq name }.empty()) {
            throw CommandException(TextComponent.of("A town with the name '$name' already exists."))
        }

        val town: TownDao = TownDao.new {
            this.name = name
            this.open = false
            this.owner = resident
        }

        resident.town = town

        val message: TextComponent = TextComponent.builder("The town of ", NamedTextColor.GRAY)
            .append(TextUI.townInfo(name))
            .append(" has been established.")
            .build()

        Sponge.getServer().sendMessage(message)
        return CommandResult.success()
    }
}