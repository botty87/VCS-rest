package com.vcs.controllers.areas2

import com.vcs.data.db.AreaItem2
import com.vcs.data.db.RetireItem2
import com.vcs.data.dbTables.*
import controllers.areas.AreasController
import controllers.referencedTables.areasCalendar.AreasCalendarController
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Areas2ControllerImpl: Areas2Controller, KoinComponent {
    override fun getAll(): List<AreaItem2> {
        return transaction {
            AreaItem2.all().sortedBy { it.name.toLowerCase() }
        }
    }

    override fun getArea(id: Int, requireTransaction: Boolean): AreaItem2 {
        return if(requireTransaction) {
            transaction {
                AreaItem2[id]
            }
        } else {
            AreaItem2[id]
        }
    }

    override fun migrate() {
        val areasController: AreasController by inject()
        val areasCalendarController: AreasCalendarController by inject()

        transaction {
            SchemaUtils.createMissingTablesAndColumns(Areas2)

            Areas2.deleteAll()
            val oldAreas = areasController.getAll()
            Areas2.batchInsert(oldAreas, shouldReturnGeneratedValues = false) { oldArea ->
                this[Areas2.id] = oldArea.id
                this[Areas2.name] = oldArea.name
                this[Areas2.towns] = oldArea.towns
                this[Areas2.depot] = oldArea.depot?.id
                this[Areas2.separatedMulti] = oldArea.separatedMulti
            }

            SchemaUtils.createMissingTablesAndColumns(
                Areas, Areas2, Depots, AreasCalendar,
                AreasTrashContainers, Retires, TrashContainers, Advices, AdvicesAreas, Retires2
            )

            val today = LocalDate.now().run {
                LocalDateTime.of(this, LocalTime.MIDNIGHT)
            }
            oldAreas.forEach { oldArea ->
                val calendarRetires = areasCalendarController.getRetiresForArea(oldArea.id.value)
                val area = getArea(oldArea.id.value)
                calendarRetires.forEach { weekDay, retireItem ->
                    var startDateTime: LocalDateTime
                    if(retireItem.startDate != null) {
                        startDateTime = LocalDateTime.of(retireItem.startDate, LocalTime.of(retireItem.time.toInt(), 0))
                    } else {
                        startDateTime = today.withHour(retireItem.time.toInt())
                        while(weekDay.toInt() != startDateTime.dayOfWeek.value) {
                            startDateTime = startDateTime.plusDays(1)
                        }
                    }
                    transaction {
                        RetireItem2.new {
                            freq = retireItem.freq
                            type = retireItem.type
                            this.startDateTime = startDateTime
                            this.area = area
                        }
                    }
                }
            }

        }
    }
}