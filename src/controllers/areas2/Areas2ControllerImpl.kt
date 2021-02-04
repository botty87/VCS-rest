package com.vcs.controllers.areas2

import com.vcs.data.db.AreaItem2
import com.vcs.data.dbTables.*
import controllers.areas.AreasController
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

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
        }
    }
}