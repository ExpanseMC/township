package com.expansemc.township.plugin.storage.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Township town role data.
 */
object TownRoleTable : IntIdTable("town_roles") {

    /**
     * The role's associated town.
     */
    val town: Column<EntityID<Int>> = reference("town", TownTable, onDelete = ReferenceOption.CASCADE).index()

    /**
     * The role's name.
     */
    val name: Column<String> = varchar("name", length = Tables.MAX_NAME_LENGTH).index()

    /**
     * The role's priority.
     *
     * Higher numbered priorities take precedence. This is used to calculate overrides.
     */
    val priority: Column<Int> = integer("priority")

    init {
        uniqueIndex(town, name)
        uniqueIndex(town, priority)
    }
}