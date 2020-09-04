package com.expansemc.township.plugin.storage.dao

import com.expansemc.township.plugin.storage.table.TownClaimTable
import com.expansemc.township.plugin.storage.table.TownWarpTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.and
import org.spongepowered.api.ResourceKey
import org.spongepowered.api.world.ServerLocation

class TownClaimDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TownClaimDao>(TownClaimTable) {
        fun getClaimAt(location: ServerLocation): TownClaimDao? =
            find {
                (TownClaimTable.world eq location.worldKey) and
                        (TownClaimTable.chunkX eq location.chunkPosition.x) and
                        (TownClaimTable.chunkZ eq location.chunkPosition.z)
            }.singleOrNull()
    }

    var world: ResourceKey by TownClaimTable.world

    var chunkX: Int by TownClaimTable.chunkX

    var chunkZ: Int by TownClaimTable.chunkZ

    var town: TownDao by TownDao referencedOn TownClaimTable.town

    val warps: SizedIterable<TownWarpDao> by TownWarpDao referrersOn TownWarpTable.claim
}