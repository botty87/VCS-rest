package com.vcs.data.dbTables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object Retires2: IntIdTable() {
    val freq = byte("freq")
    val type = byte("type")
    val startDateTime = datetime("startDateTime")
}