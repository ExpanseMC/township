package com.expansemc.township.plugin.storage.table.intermediate

import com.expansemc.township.plugin.storage.table.TownCitizenTable
import com.expansemc.township.plugin.storage.table.TownRoleTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

/**
 * An intermediate table describing a resident and their town roles.
 */
object TownCitizenRoleTable : Table("resident_town_roles") {

    /**
     * The resident.
     */
    val citizen: Column<EntityID<Int>> = reference("citizen", TownCitizenTable, onDelete = ReferenceOption.CASCADE).index()

    /**
     * The resident's town role.
     */
    val role: Column<EntityID<Int>> = reference("role", TownRoleTable, onDelete = ReferenceOption.CASCADE).index()

    override val primaryKey: PrimaryKey = PrimaryKey(citizen, role)
}