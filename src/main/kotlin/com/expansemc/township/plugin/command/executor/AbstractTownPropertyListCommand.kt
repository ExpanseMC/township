package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOwnTown
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.util.TextUI
import net.kyori.adventure.text.Component
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.service.pagination.PaginationList

abstract class AbstractTownPropertyListCommand<T : Entity<*>> : TransactionalCommandExecutor {

    abstract val titleName: String

    abstract fun getValues(town: TownDao): SizedIterable<T>

    abstract fun toComponent(value: T): Component

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val town: TownDao = context.requireOwnTown()
        val values: SizedIterable<T> = getValues(town)

        val pagination: PaginationList = TextUI.pagination("${values.count()} $titleName in ${town.name}") {
            values.mapTo(destination = this, transform = this@AbstractTownPropertyListCommand::toComponent)
        }

        pagination.sendTo(context.cause.audience)
        return CommandResult.success()
    }
}