package com.expansemc.township.plugin.storage.table

import com.expansemc.township.plugin.storage.column.ServerLocationColumn
import com.expansemc.township.plugin.storage.column.serverLocation
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*

/**
 * Township town warp data.
 */
object TownWarpTable : IntIdTable("town_warps") {

    /**
     * The warp's associated town.
     */
    val town: Column<EntityID<Int>> = reference("town", TownTable, onDelete = ReferenceOption.CASCADE).index()

    /**
     * The warp's associated town claim.
     */
    val claim: Column<EntityID<Int>> = reference("town_claim", TownClaimTable, onDelete = ReferenceOption.CASCADE).index()

    /**
     * The warp's name.
     */
    val name: Column<String> = varchar("name", length = Tables.MAX_NAME_LENGTH).index()

    /**
     * The warp's teleport location.
     */
    val location: ServerLocationColumn = serverLocation().also { it.getRealColumns().forEach { col: Column<*> -> col.index() } }

    init {
        uniqueIndex(town, name)
    }

    fun getAllNamesFor(townId: Int): SizedIterable<String> =
        TownWarpTable.slice(name)
            .select { town eq townId }
            .orderBy(name)
            .mapLazy { it[name] }
}