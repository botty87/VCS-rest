package com.vcs.sources.localDB.referencedTables.areasCalendar

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import com.vcs.data.localDB.AreaCalendarItem
import com.vcs.data.localDB.AreaItem
import com.vcs.data.localDB.RetireItem
import com.vcs.tools.WeekDayConverter
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class AreasCalendarControllerImpl: AreasCalendarController {
    override fun clear() {
        transaction {
            AreasCalendar.deleteAll()
        }
    }

    override fun setAreaRetires(areaItem: AreaItem, retires: Map<Byte, Collection<Int>>) {
        transaction {
            AreasCalendar.deleteWhere {
                AreasCalendar.area eq areaItem.id
            }

            retires.forEach { dayRetire ->
                dayRetire.value.forEach { retireId ->
                    AreaCalendarItem.new {
                        area = areaItem
                        weekDay = dayRetire.key
                        retire = RetireItem[retireId]
                    }
                }
            }
        }
    }

    override fun addNew(areaItem: AreaItem, retireItem: RetireItem, weekDayString: String) {
        transaction {
            AreaCalendarItem.new {
                area = areaItem
                retire = retireItem
                //Add 1 because different app weekday order
                this.weekDay = (WeekDayConverter.fromStringTag(weekDayString).ordinal + 1).toByte()
            }
        }
    }

    override fun getRetiresForArea(areaItem: AreaItem): Multimap<Byte, Int> {
        val calendarMap: Multimap<Byte, Int> = ArrayListMultimap.create()
        transaction {
            AreaCalendarItem.find {
                AreasCalendar.area eq areaItem.id
            }.forEach { areaCalendar ->
                calendarMap.put(areaCalendar.weekDay, areaCalendar.retire.id.value)
            }
        }
        return calendarMap
    }
}