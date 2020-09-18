package com.expansemc.township.plugin.storage.table.intermediate

import com.expansemc.township.plugin.storage.column.resourceKey
import com.expansemc.township.plugin.storage.table.TownClaimGroupTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.spongepowered.api.ResourceKey

object TownClaimGroupPermissionOverrideTable : Table("town_claim_group_permission_overrides") {

    val group: Column<EntityID<Int>> = reference("group", TownClaimGroupTable, ReferenceOption.CASCADE).index()

    val permission: Column<ResourceKey> = resourceKey("permission").index()

    val value: Column<Boolean> = bool("value")

    override val primaryKey: PrimaryKey = PrimaryKey(group, permission)
}