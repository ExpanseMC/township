package com.expansemc.township.plugin.storage.table.intermediate

import com.expansemc.township.plugin.storage.table.TownRoleTable
import com.expansemc.township.plugin.storage.table.TownWarpTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

/**
 * An intermediate table describing a warp and its permitted roles.
 */
object TownWarpRoleTable : Table("town_warp_roles") {

    /**
     * The warp.
     */
    val warp: Column<EntityID<Int>> = reference("warp", TownWarpTable, onDelete = ReferenceOption.CASCADE).index()

    /**
     * The warp's permitted role.
     */
    val role: Column<EntityID<Int>> = reference("role", TownRoleTable, onDelete = ReferenceOption.CASCADE).index()

    override val primaryKey: PrimaryKey = PrimaryKey(warp, role)
}