package com.expansemc.township.plugin.storage.dao

import com.expansemc.township.plugin.storage.table.TownCitizenTable
import com.expansemc.township.plugin.storage.table.intermediate.TownCitizenRoleTable
import com.expansemc.township.plugin.storage.table.intermediate.TownResidentInviteTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable

/**
 * @see TownCitizenTable
 */
class TownCitizenDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TownCitizenDao>(TownCitizenTable)

    /**
     * This citizen's town.
     */
    var town: TownDao? by TownDao optionalReferencedOn TownCitizenTable.town

    /**
     * This citizen's resident data.
     */
    var resident: ResidentDao by ResidentDao referencedOn TownCitizenTable.resident

    /**
     * This citizen's town roles.
     *
     * @see TownCitizenRoleTable
     */
    var roles: SizedIterable<TownRoleDao> by TownRoleDao.via(TownCitizenRoleTable.citizen, TownCitizenRoleTable.role)

    /**
     * The residents that have been invited to this citizen's town, by this citizen.
     *
     * @see TownResidentInviteTable
     */
    var invited: SizedIterable<ResidentDao> by ResidentDao.via(TownResidentInviteTable.citizen, TownResidentInviteTable.resident)
}