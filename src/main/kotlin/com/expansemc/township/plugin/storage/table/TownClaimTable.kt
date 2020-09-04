package com.expansemc.township.plugin.storage.table

import com.expansemc.township.plugin.storage.column.resourceKey
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.spongepowered.api.ResourceKey

/**
 * Township town claim data.
 */
object TownClaimTable : IntIdTable("town_claims") {

    /**
     * The claim's associated world.
     */
    val world: Column<ResourceKey> = resourceKey("world").index()

    /**
     * The claim's x coordinate chunk-wise.
     */
    val chunkX: Column<Int> = integer("chunk_x").index()

    /**
     * The claim's z coordinate chunk-wise.
     */
    val chunkZ: Column<Int> = integer("chunk_z").index()

    /**
     * The claim's associated town.
     */
    val town: Column<EntityID<Int>> = reference("town", TownTable, onDelete = ReferenceOption.CASCADE).index()

    init {
        uniqueIndex(world, chunkX, chunkZ)
    }
}