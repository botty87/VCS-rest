package com.vcs.data.dbTables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date

object Retires: IntIdTable() {
    val freq = byte("freq")
    val time = byte("time")
    val type = byte("type")
    val name = text("name").uniqueIndex()
    val startDate = date("startDate").nullable()
}