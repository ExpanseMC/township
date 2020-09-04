package com.expansemc.township.plugin.storage.table.intermediate

import com.expansemc.township.plugin.storage.column.resourceKey
import com.expansemc.township.plugin.storage.table.TownRoleTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.spongepowered.api.ResourceKey

/**
 * An intermediate table describing a town role and its permissions.
 */
object TownRolePermissionTable : Table("town_role_permissions") {

    /**
     * The town role.
     */
    val townRole: Column<EntityID<Int>> = reference("town_role", TownRoleTable, onDelete = ReferenceOption.CASCADE).index()

    /**
     * The town role's permission.
     */
    val permission: Column<ResourceKey> = resourceKey("permission")

    init {
        uniqueIndex(townRole, permission)
    }
}