package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.Parameters
import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOne
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.util.TextUI
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.service.pagination.PaginationList

object CommandTownInfo : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val town: TownDao = context.requireOne(Parameters.townOrOwn)

        val pagination: PaginationList = getPagination(town)

        pagination.sendTo(context.cause.audience)
        return CommandResult.success()
    }

    internal fun getPagination(town: TownDao): PaginationList =
        TextUI.pagination("Town: ${town.name}") {
            add(TextUI.property("Name", town.name))
            town.owner.let {
                if (it == null) {
                    add(TextUI.property("Owner", "<none>"))
                } else {
                    add(
                        TextUI.clickableProperty(
                            name = "Owner",
                            value = it.resident.name,
                            hover = "Click here to see owner information.",
                            command = "/township:resident info ${it.resident.name}"
                        )
                    )
                }
            }
            add(TextUI.boolProperty("Open", town.open))
            add(TextUI.timestampProperty("Created-At", town.createdAt))
            add(
                TextUI.clickableProperty(
                    name = "Residents",
                    value = "${town.citizens.count()} residents",
                    hover = "Click here to see all residents.",
                    command = "/township:town residents ${town.name}"
                )
            )
            add(
                TextUI.clickableProperty(
                    name = "Claims",
                    value = "${town.claims.count()} claims",
                    hover = "Click here to see all claims.",
                    command = "/township:town claim list"
                )
            )
            add(
                TextUI.clickableProperty(
                    name = "Warps",
                    value = "${town.warps.count()} warps",
                    hover = "Click here to see all warps.",
                    command = "/township:town warp list"
                )
            )
        }
}