package com.expansemc.township.plugin.command

import com.expansemc.township.plugin.Township
import com.expansemc.township.plugin.storage.*
import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.storage.dao.TownWarpDao
import com.expansemc.township.plugin.storage.table.ResidentTable
import com.expansemc.township.plugin.storage.table.TownTable
import com.expansemc.township.plugin.storage.table.TownWarpTable
import com.expansemc.township.plugin.util.unwrap
import net.kyori.adventure.text.TextComponent
import org.jetbrains.exposed.sql.selectAll
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.command.parameter.Parameter
import org.spongepowered.api.entity.living.player.server.ServerPlayer

object Parameters {

    val chunkX: Parameter.Value<Int> by lazy {
        Parameter.integerNumber()
            .setKey("chunk-x")
            .build()
    }

    val chunkZ: Parameter.Value<Int> by lazy {
        Parameter.integerNumber()
            .setKey("chunk-z")
            .build()
    }

    val name: Parameter.Value<String> by lazy {
        Parameter.string()
            .setKey("name")
            .build()
    }

    val open: Parameter.Value<Boolean> by lazy {
        Parameter.bool()
            .setKey("open")
            .optional()
            .build()
    }

    val town: CustomParameter<String, TownDao> by lazy {
        town("town").build().map(::getTown)
    }

    val townOrOwn: CustomParameter<String?, TownDao> by lazy {
        town("town").optional().build().map(::getTownOrOwn)
    }

    val townWarp: CustomParameter<String, TownWarpDao> by lazy {
        townWarp("warp").build().map(::getTownWarp)
    }

    val resident: CustomParameter<String, ResidentDao> by lazy {
        resident("resident").build().map(::getResident)
    }

    val residentOrSelf: CustomParameter<String?, ResidentDao> by lazy {
        resident("resident").optional().build().map(::getResidentOrSelf)
    }

    val friend: CustomParameter<String, ResidentDao> by lazy {
        resident("friend").build().map(::getResident)
    }

    private fun town(key: String): Parameter.Value.Builder<String> =
        Parameter.string()
            .setKey(key)
            .setSuggestions {
                Township.DATABASE.transaction {
                    TownTable.getAllNames().toList()
                }
            }

    private fun townWarp(key: String) : Parameter.Value.Builder<String> =
        Parameter.string()
            .setKey(key)
            .setSuggestions {
                Township.DATABASE.transaction {
                    TownWarpTable.getAllNamesFor(it.getOwnTown()?.id?.value ?: return@transaction emptyList())
                        .toList()
                }
            }

    private fun resident(key: String): Parameter.Value.Builder<String> =
        Parameter.string()
            .setKey(key)
            .setSuggestions {
                Township.DATABASE.transaction {
                    ResidentTable.slice(ResidentTable.name)
                        .selectAll()
                        .orderBy(ResidentTable.name)
                        .map { it[ResidentTable.name] }
                }
            }

    private fun getTown(context: CommandContext, name: String): TownDao =
        Township.DATABASE.transaction {
            TownDao.findByName(name)
                ?: throw CommandException(TextComponent.of("No town with the name '$name' exists."))
        }

    private fun getTownOrOwn(context: CommandContext, name: String?): TownDao =
        Township.DATABASE.transaction {
            if (name != null) {
                TownDao.findByName(name)
                    ?: throw CommandException(TextComponent.of("No town with the name '$name' exists."))
            } else {
                val player: ServerPlayer = context.cause.first(ServerPlayer::class.java).unwrap()
                    ?: throw CommandException(TextComponent.of("You must specify a town name."))
                val resident: ResidentDao = ResidentDao.findByPlayerId(player.uniqueId)
                    ?: throw CommandException(TextComponent.of("You must specify a town name."))
                resident.town
                    ?: throw CommandException(TextComponent.of("You must specify a town name."))
            }
        }

    private fun getTownWarp(context: CommandContext, name: String): TownWarpDao =
        Township.DATABASE.transaction {
            TownWarpDao.find(context.requireOwnTown(), name)
                ?: throw CommandException(TextComponent.of("No town warp with the name '$name' exists."))
        }

    private fun getResident(context: CommandContext, name: String): ResidentDao =
        Township.DATABASE.transaction {
            ResidentDao.findByName(name)
                ?: throw CommandException(TextComponent.of("No resident with the name '$name' exists."))
        }

    private fun getResidentOrSelf(context: CommandContext, name: String?): ResidentDao =
        Township.DATABASE.transaction {
            if (name != null) {
                ResidentDao.findByName(name)
                    ?: throw CommandException(TextComponent.of("No resident with the name '$name' exists."))
            } else {
                val player: ServerPlayer = context.cause.first(ServerPlayer::class.java).unwrap()
                    ?: throw CommandException(TextComponent.of("You must specify a town name."))
                ResidentDao.findByPlayerId(player.uniqueId)
                    ?: throw CommandException(TextComponent.of("You must specify a town name."))
            }
        }
}