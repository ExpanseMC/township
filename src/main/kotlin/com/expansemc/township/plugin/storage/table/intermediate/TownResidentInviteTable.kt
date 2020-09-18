package com.expansemc.township.plugin.storage.table.intermediate

import com.expansemc.township.plugin.storage.table.ResidentTable
import com.expansemc.township.plugin.storage.table.TownCitizenTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object TownResidentInviteTable : Table("town_resident_invites") {

    /**
     * The citizen that created the invite.
     */
    val citizen: Column<EntityID<Int>> = reference("citizen", TownCitizenTable, onDelete = ReferenceOption.CASCADE).index()

    /**
     * The resident that was invited.
     */
    val resident: Column<EntityID<Int>> = reference("resident", ResidentTable, onDelete = ReferenceOption.CASCADE).index()

    override val primaryKey: PrimaryKey = PrimaryKey(citizen, resident)
}