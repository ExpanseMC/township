package com.expansemc.township.plugin.storage.dao

import com.expansemc.township.plugin.storage.table.intermediate.ResidentFriendTable
import com.expansemc.township.plugin.storage.table.ResidentTable
import com.expansemc.township.plugin.storage.table.TownCitizenTable
import com.expansemc.township.plugin.storage.table.intermediate.TownCitizenRoleTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import java.util.*

class ResidentDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ResidentDao>(ResidentTable) {

        fun findByPlayerId(playerId: UUID): ResidentDao? =
            find { ResidentTable.playerId eq playerId }.singleOrNull()

        fun findByName(name: String): ResidentDao? =
            find { ResidentTable.name eq name }.singleOrNull()
    }

    /**
     * @see ResidentTable.playerId
     */
    var playerId: UUID? by ResidentTable.playerId

    /**
     * @see ResidentTable.name
     */
    var name: String by ResidentTable.name

    /**
     * @see ResidentTable.town
     */
    val citizen: TownCitizenDao? by TownCitizenDao optionalBackReferencedOn TownCitizenTable.resident

    /**
     * @see ResidentFriendTable
     */
    var friends: SizedIterable<ResidentDao> by ResidentDao.via(ResidentFriendTable.resident, ResidentFriendTable.friend)

    /**
     * @see ResidentFriendTable
     */
    var reverseFriends: SizedIterable<ResidentDao> by ResidentDao.via(ResidentFriendTable.friend, ResidentFriendTable.resident)

    fun addFriend(friend: ResidentDao) {
        ResidentFriendTable.insert {
            it[ResidentFriendTable.resident] = this@ResidentDao.id
            it[ResidentFriendTable.friend] = friend.id
        }
    }

    fun removeFriend(friend: ResidentDao) {
        ResidentFriendTable.deleteWhere {
            (ResidentFriendTable.resident eq this@ResidentDao.id) and (ResidentFriendTable.friend eq friend.id)
        }
    }

    fun removeAllFriends() {
        ResidentFriendTable.deleteWhere {
            ResidentFriendTable.resident eq this@ResidentDao.id
        }
    }
}