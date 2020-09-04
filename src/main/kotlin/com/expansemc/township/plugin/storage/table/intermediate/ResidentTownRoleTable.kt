package com.expansemc.township.plugin.storage.table.intermediate

import com.expansemc.township.plugin.storage.table.ResidentTable
import com.expansemc.township.plugin.storage.table.TownRoleTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

/**
 * An intermediate table describing a resident and their town roles.
 */
object ResidentTownRoleTable : Table("resident_town_roles") {

    /**
     * The resident.
     */
    val resident: Column<EntityID<Int>> = reference("resident", ResidentTable, onDelete = ReferenceOption.CASCADE).index()

    /**
     * The resident's town role.
     */
    val townRole: Column<EntityID<Int>> = reference("town_role", TownRoleTable, onDelete = ReferenceOption.CASCADE).index()
}