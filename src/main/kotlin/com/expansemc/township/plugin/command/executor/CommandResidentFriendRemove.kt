package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.Parameters
import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireOne
import com.expansemc.township.plugin.command.requireSelfResident
import com.expansemc.township.plugin.storage.dao.ResidentDao
import net.kyori.adventure.text.TextComponent
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext

object CommandResidentFriendRemove : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val friend: ResidentDao = context.requireOne(Parameters.friend)
        val resident: ResidentDao = context.requireSelfResident()

        if (resident.id.value == friend.id.value) {
            throw CommandException(TextComponent.of("You can't become friends with yourself."))
        }

        resident.removeFriend(friend)
        context.sendMessage(TextComponent.of("${resident.name} is no longer friends with ${friend.name}."))
        return CommandResult.success()
    }
}