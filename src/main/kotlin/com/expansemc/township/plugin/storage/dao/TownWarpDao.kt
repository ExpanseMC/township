package com.expansemc.township.plugin.storage.dao

import com.expansemc.township.plugin.storage.table.intermediate.TownWarpRoleTable
import com.expansemc.township.plugin.storage.table.TownWarpTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.and
import org.spongepowered.api.world.ServerLocation

class TownWarpDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TownWarpDao>(TownWarpTable) {

        fun find(town: TownDao, name: String): TownWarpDao? =
            TownWarpDao.find { (TownWarpTable.town eq town.id) and (TownWarpTable.name eq name) }
                .singleOrNull()
    }

    var town: TownDao by TownDao referencedOn TownWarpTable.town

    var claim: TownClaimDao by TownClaimDao referencedOn TownWarpTable.claim

    var name: String by TownWarpTable.name

    /**
     *
     */
    var location: ServerLocation by TownWarpTable.location

    /**
     * The roles permitted to use this warp.
     */
    var roles: SizedIterable<TownRoleDao>
            by TownRoleDao.via(TownWarpRoleTable.warp, TownWarpRoleTable.role)
}