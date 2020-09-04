package com.expansemc.township.plugin.storage.table

import com.expansemc.township.plugin.Township
import com.expansemc.township.plugin.storage.table.intermediate.ResidentFriendTable
import com.expansemc.township.plugin.storage.table.intermediate.TownRolePermissionTable
import com.expansemc.township.plugin.storage.transaction
import org.jetbrains.exposed.sql.SchemaUtils

object Tables {

    const val MAX_NAME_LENGTH = 64

    fun initialize() {
        Township.DATABASE.transaction {
            SchemaUtils.createMissingTablesAndColumns(
                ResidentFriendTable,
                ResidentTable,
                TownClaimTable,
                TownRolePermissionTable,
                TownRoleTable,
                TownTable,
                TownWarpTable
            )
        }
    }
}