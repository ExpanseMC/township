package com.expansemc.township.plugin.storage.util

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ColumnLink<Target, REF : Comparable<REF>>(
    private val table: Table,
    private val targetColumn: Column<Target>,
    private val refColumn: Column<EntityID<REF>>
) : ReadOnlyProperty<Entity<REF>, SizedIterable<Target>> {

    override fun getValue(thisRef: Entity<REF>, property: KProperty<*>): SizedIterable<Target> =
        this.table.slice(this.targetColumn)
            .select { this@ColumnLink.refColumn eq thisRef.id }
            .mapLazy { it[this.targetColumn] }
}

infix fun <Target, REF : Comparable<REF>> Column<Target>.columnBackReferencedOn(refColumn: Column<EntityID<REF>>): ColumnLink<Target, REF> =
    ColumnLink(this.table, this, refColumn)


class BiColumnLink<T1, T2, REF : Comparable<REF>>(
    private val table: Table,
    private val targetColumn1: Column<T1>,
    private val targetColumn2: Column<T2>,
    private val refColumn: Column<EntityID<REF>>
) : ReadOnlyProperty<Entity<REF>, SizedIterable<Pair<T1, T2>>> {

    override fun getValue(thisRef: Entity<REF>, property: KProperty<*>): SizedIterable<Pair<T1, T2>> =
        this.table.slice(this.targetColumn1, this.targetColumn2)
            .select { this@BiColumnLink.refColumn eq thisRef.id }
            .mapLazy { it[this.targetColumn1] to it[this.targetColumn2] }
}

infix fun <T1, T2, REF : Comparable<REF>> Pair<Column<T1>, Column<T2>>.columnsBackReferencedOn(refColumn: Column<EntityID<REF>>): BiColumnLink<T1, T2, REF> =
    BiColumnLink(this.first.table, this.first, this.second, refColumn)