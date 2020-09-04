package com.expansemc.township.plugin.storage.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.CurrentTimestamp
import org.jetbrains.exposed.sql.`java-time`.timestamp
import java.time.Instant

/**
 * Township town data.
 */
object TownTable : IntIdTable("towns") {

    /**
     * The town's name.
     */
    val name: Column<String> = varchar("name", length = 64).uniqueIndex()

    /**
     * The town's citizen owner.
     *
     * Note: Citizens who own towns cannot have their data deleted while they are still owner.
     */
    val owner: Column<EntityID<Int>> = reference("owner", ResidentTable, onDelete = ReferenceOption.RESTRICT).uniqueIndex()

    /**
     * Whether the town is joinable without invitation.
     */
    val open: Column<Boolean> = bool("open").default(false)

    /**
     * The town's creation date and time.
     */
    val createdAt: Column<Instant> = timestamp("created_at").defaultExpression(CurrentTimestamp())

    fun getAllNames(): SizedIterable<String> =
        slice(name).selectAll().orderBy(name).mapLazy { it[name] }
}