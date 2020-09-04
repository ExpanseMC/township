package com.expansemc.township.plugin.storage.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

/**
 * Township resident data.
 */
object ResidentTable : IntIdTable("residents") {

    /**
     * If the resident represents a player, this column stores their associated [UUID].
     */
    val playerId: Column<UUID?> = uuid("player_id").nullable().uniqueIndex()

    /**
     * The resident's name.
     */
    val name: Column<String> = varchar("name", length = Tables.MAX_NAME_LENGTH)

    /**
     * The resident's town, if they're in one.
     */
    val town: Column<EntityID<Int>?> = optReference("town", TownTable, onDelete = ReferenceOption.SET_NULL)
}