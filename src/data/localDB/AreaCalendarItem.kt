package com.vcs.data.localDB

import com.vcs.sources.localDB.referencedTables.areasCalendar.AreasCalendar
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AreaCalendarItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AreaCalendarItem>(AreasCalendar)
    var area by AreaItem referencedOn AreasCalendar.area
    var retire by RetireItem referencedOn AreasCalendar.retire
    var weekDay by AreasCalendar.weekDay
}