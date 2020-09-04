package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.command.TransactionalCommandExecutor
import com.expansemc.township.plugin.command.requireSelfResident
import com.expansemc.township.plugin.storage.dao.ResidentDao
import net.kyori.adventure.text.TextComponent
import org.jetbrains.exposed.sql.Transaction
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext

object CommandResidentFriendClear : TransactionalCommandExecutor {

    override fun Transaction.execute(context: CommandContext): CommandResult {
        val resident: ResidentDao = context.requireSelfResident()

        resident.removeAllFriends()
        context.sendMessage(TextComponent.of("Removed all friends."))
        return CommandResult.success()
    }
}