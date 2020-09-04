package com.expansemc.township.plugin.storage.util

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ColumnLink<Target, RID : Comparable<RID>>(
    private val table: Table,
    private val targetColumn: Column<Target>,
    private val refColumn: Column<EntityID<RID>>
) : ReadOnlyProperty<Entity<RID>, SizedIterable<Target>> {

    override fun getValue(thisRef: Entity<RID>, property: KProperty<*>): SizedIterable<Target> =
        this.table.slice(this.targetColumn)
            .select { this@ColumnLink.refColumn eq thisRef.id }
            .mapLazy { it[this.targetColumn] }
}

infix fun <Target, RID : Comparable<RID>> Column<Target>.columnReferencedOn(refColumn: Column<EntityID<RID>>): ColumnLink<Target, RID> =
    ColumnLink(this.table, this, refColumn)
