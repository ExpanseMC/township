package com.expansemc.township.plugin.storage.dao

import com.expansemc.township.plugin.storage.table.TownClaimGroupTable
import com.expansemc.township.plugin.storage.table.intermediate.TownClaimGroupPermissionOverrideTable
import com.expansemc.township.plugin.storage.table.intermediate.TownClaimGroupPermissionOverrideTable.group
import com.expansemc.township.plugin.storage.table.intermediate.TownClaimGroupPermissionOverrideTable.permission
import com.expansemc.township.plugin.storage.table.intermediate.TownClaimGroupPermissionOverrideTable.value
import com.expansemc.township.plugin.storage.util.columnsBackReferencedOn
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import org.spongepowered.api.ResourceKey

class TownClaimGroupDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TownClaimGroupDao>(TownClaimGroupTable)

    var town: TownDao by TownDao referencedOn TownClaimGroupTable.town

    var name: String by TownClaimGroupTable.name

    /**
     * @see TownClaimGroupPermissionOverrideTable
     */
    val permissionOverrides: SizedIterable<Pair<ResourceKey, Boolean>> by (permission to value) columnsBackReferencedOn group
}