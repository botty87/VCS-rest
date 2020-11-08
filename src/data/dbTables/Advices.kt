package com.vcs.data.dbTables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date

object Advices : IntIdTable() {
    val message = text("message")
    val start = date("start")
    val end = date("end")
}