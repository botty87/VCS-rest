package com.vcs.data.dbTables

import org.jetbrains.exposed.dao.id.IntIdTable

object Users: IntIdTable() {
    val username = text("username").uniqueIndex()
    val password = text("password")
    val active = bool("active")
}