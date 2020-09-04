package com.expansemc.township.plugin.storage.column

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.TextColumnType
import org.spongepowered.api.ResourceKey

fun Table.resourceKey(name: String): Column<ResourceKey> = registerColumn(name, ResourceKeyColumnType())

class ResourceKeyColumnType : TextColumnType() {
    override fun notNullValueToDB(value: Any): String = when (value) {
        is String -> value
        is ResourceKey -> value.formatted
        else -> error("Unexpected value: $value of ${value::class.qualifiedName}")
    }

    override fun valueFromDB(value: Any): ResourceKey = when (value) {
        is ResourceKey -> value
        else -> ResourceKey.resolve(super.valueFromDB(value).toString())
    }
}