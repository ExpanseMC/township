package com.expansemc.township.plugin.storage.column

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.CompositeColumn
import org.jetbrains.exposed.sql.Table
import org.spongepowered.api.ResourceKey
import org.spongepowered.api.world.ServerLocation

data class ServerLocationColumn(
    val world: Column<ResourceKey>,
    val x: Column<Int>,
    val y: Column<Int>,
    val z: Column<Int>
) : CompositeColumn<ServerLocation>() {

    override fun getRealColumns(): List<Column<*>> = listOf(this.world, this.x, this.y, this.z)

    override fun getRealColumnsWithValues(compositeValue: ServerLocation): Map<Column<*>, Any?> =
        mapOf(
            this.world to compositeValue.worldKey,
            this.x to compositeValue.blockX,
            this.y to compositeValue.blockY,
            this.z to compositeValue.blockZ
        )

    override fun restoreValueFromParts(parts: Map<Column<*>, Any?>): ServerLocation {
        val world: ResourceKey = when (val world: Any? = parts[this.world]) {
            is ResourceKey -> world
            else -> ResourceKey.resolve(world.toString())
        }
        val x: Int = parts[this.x] as Int
        val y: Int = parts[this.y] as Int
        val z: Int = parts[this.z] as Int

        return ServerLocation.of(world, x, y, z)
    }
}

fun Table.serverLocation(
    world: Column<ResourceKey> = this.resourceKey("world"),
    x: Column<Int> = this.integer("x"),
    y: Column<Int> = this.integer("y"),
    z: Column<Int> = this.integer("z")
): ServerLocationColumn = ServerLocationColumn(world, x, y, z)