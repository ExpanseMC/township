package com.expansemc.township.plugin.storage.table.intermediate

import com.expansemc.township.plugin.storage.table.ResidentTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

/**
 * An intermediate table describing a resident and their friends.
 */
object ResidentFriendTable : Table("resident_friends") {

    /**
     * The resident.
     */
    val resident: Column<EntityID<Int>> = reference("resident", ResidentTable, onDelete = ReferenceOption.CASCADE).index()

    /**
     * The resident's friend.
     */
    val friend: Column<EntityID<Int>> = reference("friend", ResidentTable, onDelete = ReferenceOption.CASCADE)
}