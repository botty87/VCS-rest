package controllers.areas

import com.vcs.data.dbTables.Areas
import com.vcs.data.json.AreaItemJson
import data.db.AreaItem
import data.db.DepotItem
import data.db.TrashContainerItem
import controllers.referencedTables.areasCalendar.AreasCalendarController
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
                depot = areaItemJson.depotId?.run{ DepotItem[this] }
                trashContainers = TrashContainerItem.forIds(areaItemJson.trashContainerIds)
                areasCalendarController.setAreaRetires(this, areaItemJson.calendarMap)
            }
        }
    }
}