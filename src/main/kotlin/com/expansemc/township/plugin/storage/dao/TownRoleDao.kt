package com.expansemc.township.plugin.storage.dao

import com.expansemc.township.plugin.storage.table.intermediate.TownRolePermissionTable
import com.expansemc.township.plugin.storage.table.TownRoleTable
import com.expansemc.township.plugin.storage.table.intermediate.TownCitizenRoleTable
import com.expansemc.township.plugin.storage.table.intermediate.TownWarpRoleTable
import com.expansemc.township.plugin.storage.util.columnBackReferencedOn
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import org.spongepowered.api.ResourceKey

class TownRoleDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TownRoleDao>(TownRoleTable)

    var town: TownDao by TownDao referencedOn TownRoleTable.town

    var name: String by TownRoleTable.name

    var priority: Int by TownRoleTable.priority

    /**
     * The permissions granted by this role.
     */
    val permissions: SizedIterable<ResourceKey>
            by TownRolePermissionTable.permission columnBackReferencedOn TownRolePermissionTable.role

    /**
     * The residents who have this role.
     */
    var residents: SizedIterable<TownCitizenDao>
            by TownCitizenDao.via(TownCitizenRoleTable.role, TownCitizenRoleTable.citizen)

    /**
     * The warps permitted by this role.
     */
    var warps: SizedIterable<TownWarpDao>
            by TownWarpDao.via(TownWarpRoleTable.role, TownWarpRoleTable.warp)
}