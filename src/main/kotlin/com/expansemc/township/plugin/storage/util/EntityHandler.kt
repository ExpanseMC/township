package com.expansemc.township.plugin.storage.util

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.sql.Transaction

interface EntityHandler<ID: Comparable<ID>, E : Entity<ID>> {
    fun <T> handle(id: ID, block: Transaction.(E) -> T): T
}