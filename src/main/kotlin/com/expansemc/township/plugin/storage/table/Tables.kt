package com.expansemc.township.plugin.storage.table

import com.expansemc.township.plugin.Township
import com.expansemc.township.plugin.storage.table.intermediate.*
import com.expansemc.township.plugin.storage.transaction
import org.jetbrains.exposed.sql.SchemaUtils

object Tables {

    const val MAX_NAME_LENGTH = 64

    fun initialize() {
        Township.DATABASE.transaction {
            SchemaUtils.createMissingTablesAndColumns(
                ResidentFriendTable,
                TownCitizenRoleTable,
                TownClaimGroupPermissionOverrideTable,
                TownResidentInviteTable,
                TownRolePermissionTable,
                TownWarpRoleTable,
                ResidentTable,
                TownClaimGroupTable,
                TownClaimTable,
                TownRoleTable,
                TownTable,
                TownWarpTable
            )
        }
    }
}