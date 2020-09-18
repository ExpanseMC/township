package com.expansemc.township.plugin.storage.dao

import com.expansemc.township.plugin.storage.table.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.time.Instant

class TownDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TownDao>(TownTable) {

        fun findByName(name: String): TownDao? =
            find { TownTable.name eq name }.singleOrNull()
    }

    var name: String by TownTable.name

    var owner: TownCitizenDao by TownCitizenDao referencedOn TownTable.owner

    var open: Boolean by TownTable.open

    var createdAt: Instant by TownTable.createdAt

    val claims: SizedIterable<TownClaimDao> by TownClaimDao referrersOn TownClaimTable.town

    val claimGroups: SizedIterable<TownClaimGroupDao> by TownClaimGroupDao referrersOn TownClaimGroupTable.town

    val citizens: SizedIterable<TownCitizenDao> by TownCitizenDao optionalReferrersOn TownCitizenTable.town

    val roles: SizedIterable<TownRoleDao> by TownRoleDao referrersOn TownRoleTable.town

    val warps: SizedIterable<TownWarpDao> by TownWarpDao referrersOn TownWarpTable.town
}