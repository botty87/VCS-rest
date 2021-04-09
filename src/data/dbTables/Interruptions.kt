package com.vcs.data.dbTables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime

object Interruptions: IntIdTable() {
    //val retires = reference("retire", Retires2)
    val whenDate = date("when")
    val newDateTime = datetime("newDateTime").nullable()
}