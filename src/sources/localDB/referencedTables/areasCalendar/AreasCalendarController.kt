package com.vcs.sources.localDB.referencedTables.areasCalendar

import com.google.common.collect.Multimap
import com.vcs.data.localDB.AreaItem
import com.vcs.data.localDB.RetireItem

interface AreasCalendarController {
    fun clear()
    fun addNew(areaItem: AreaItem, retireItem: RetireItem, weekDayString: String)
    fun getRetiresForArea(areaItem: AreaItem): Multimap<Byte, Int>
}