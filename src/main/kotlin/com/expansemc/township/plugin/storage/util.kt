package com.expansemc.township.plugin.storage

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction

fun <T> Database.transaction(statement: Transaction.() -> T): T =
    org.jetbrains.exposed.sql.transactions.transaction(this, statement)