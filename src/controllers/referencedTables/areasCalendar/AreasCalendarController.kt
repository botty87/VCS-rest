package controllers.referencedTables.areasCalendar

import com.google.common.collect.Multimap
import data.db.AreaItem
import data.db.RetireItem

interface AreasCalendarController {
    fun setAreaRetires(areaItem: AreaItem, retires: Map<Byte, Collection<Int>>)
    fun addNew(areaItem: AreaItem, retireItem: RetireItem, weekDayString: String)
    fun getRetiresForArea(areaId: Int): Multimap<Byte, Int>
}