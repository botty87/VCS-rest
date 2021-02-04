package com.vcs.data.dbTables

import org.jetbrains.exposed.dao.id.IntIdTable

object Areas2: IntIdTable() {
    val name = Areas2.text("name").uniqueIndex()
    val towns = Areas2.text("towns").nullable()
    val depot = Areas2.reference("depot", Depots).nullable()
    val separatedMulti = Areas2.bool("separated_multi").default(false)
}