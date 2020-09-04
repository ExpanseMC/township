package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.Parameters
import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOne
import com.expansemc.township.plugin.storage.dao.TownWarpDao
import com.expansemc.township.plugin.util.TextUI
import com.expansemc.township.plugin.util.unwrap
import net.kyori.adventure.text.TextComponent
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.entity.Entity

object CommandTownWarpTeleport : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val entity: Entity = context.cause.first(Entity::class.java).unwrap()
            ?: throw CommandException(TextComponent.of("Must be an entity to use this command!"))

        val warp: TownWarpDao = context.requireOne(Parameters.townWarp)

        entity.setLocation(warp.location)

        context.sendMessage(TextComponent.of("Warped to ")
            .append(TextUI.townWarpInfo(warp.name)))
        return CommandResult.success()
    }
}