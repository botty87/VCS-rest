package com.vcs.sources.localDB.areas

import com.vcs.sources.localDB.depots.Depots
import org.jetbrains.exposed.dao.id.IntIdTable

object Areas: IntIdTable() {
    val name = Areas.text("name").uniqueIndex()
    val towns = Areas.text("towns").nullable()
    val depot = Areas.reference("depot", Depots).nullable()
}