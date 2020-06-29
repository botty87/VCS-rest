package com.vcs.sources.localDB.referencedTables.areasCalendar

import com.vcs.sources.localDB.areas.Areas
import com.vcs.sources.localDB.retires.Retires
import org.jetbrains.exposed.dao.id.IntIdTable

object AreasCalendar: IntIdTable() {
    val area = reference("area", Areas).index()
    val retire = reference("retire", Retires)
    val weekDay = byte("weekDay")
    override val primaryKey = PrimaryKey(area, retire, weekDay)
}