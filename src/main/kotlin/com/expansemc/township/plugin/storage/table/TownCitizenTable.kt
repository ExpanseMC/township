package com.expansemc.township.plugin.storage.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption

object TownCitizenTable : IntIdTable("town_citizens") {
    val town: Column<EntityID<Int>?> = optReference("town", TownTable, onDelete = ReferenceOption.CASCADE)
    val resident: Column<EntityID<Int>> = reference("resident", ResidentTable, onDelete = ReferenceOption.CASCADE)
}