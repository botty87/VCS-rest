package com.vcs.sources.localDB.areas

import com.vcs.data.json.AreaItemJson
import com.vcs.data.localDB.AreaItem
import com.vcs.data.localDB.DepotItem
import com.vcs.data.localDB.TrashContainerItem
import com.vcs.sources.localDB.referencedTables.areasCalendar.AreasCalendarController
import com.vcs.sources.localDB.retires.RetiresController
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class AreasControllerImpl: AreasController, KoinComponent {
    private val areasCalendarController: AreasCalendarController by inject()

    override fun getAll(): List<AreaItem> {
        return transaction {
            AreaItem.all().toList()
        }
    }

    override fun clear() {
        transaction {
            Areas.deleteAll()
        }
    }

    override fun update(areaItemJson: AreaItemJson) : AreaItem {
        return transaction {
            AreaItem[areaItemJson.id].apply {
                name = areaItemJson.name
                towns = areaItemJson.towns
                depot = areaItemJson.depotId?.run{DepotItem[this] }
                trashContainers = TrashContainerItem.forIds(areaItemJson.trashContainerIds)
                areasCalendarController.setAreaRetires(this, areaItemJson.calendarMap)
            }
        }
    }
}