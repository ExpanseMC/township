package com.expansemc.township.plugin.storage.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption

object TownClaimGroupTable : IntIdTable("town_claim_groups") {

    val town: Column<EntityID<Int>> = reference("town", TownTable, onDelete = ReferenceOption.CASCADE)

    val name: Column<String> = varchar("name", length = Tables.MAX_NAME_LENGTH)
}