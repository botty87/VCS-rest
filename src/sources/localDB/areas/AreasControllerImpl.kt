package com.vcs.sources.localDB.areas

import com.google.common.collect.Multimap
import com.vcs.data.firestoreDB.AreaItemFS
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
    private val retiresController: RetiresController by inject()
    private val areasCalendarController: AreasCalendarController by inject()
    //private val areasTrashContainersController: AreasTrashContainersController by inject()

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

    override fun putFS(areaItemsFS: List<AreaItemFS>, depotItemsMap: Map<Int, DepotItem>, trashContainersTownMap: Multimap<String, TrashContainerItem>) {
        val retires = retiresController.getAll()

        transaction {
            areaItemsFS.sortedBy { it.name }.forEach { areaItemFS ->
                val areaItem = AreaItem.new {
                    name = areaItemFS.name
                    towns = areaItemFS.towns?.run {
                        val townsBuilder = StringBuilder()
                        val townsIterator = iterator()
                        while (townsIterator.hasNext()) {
                            townsBuilder.append(townsIterator.next())
                            if(townsIterator.hasNext()) {
                                townsBuilder.append(", ")
                            }
                        }
                        townsBuilder.toString()
                    }
                    depot = depotItemsMap[areaItemFS.id]
                }

                areaItemFS.calendarMap.keySet().forEach { weekDay ->
                    areaItemFS.calendarMap[weekDay].forEach { retireID ->
                        val retireItem = retires.first { it.name == retireID }
                        areasCalendarController.addNew(areaItem, retireItem, weekDay)
                    }
                }

                if(areaItemFS.name.startsWith("Darfo")) {
                    areaItem.trashContainers = SizedCollection(trashContainersTownMap["Darfo Boario Terme"])
                } else {
                    areaItem.trashContainers = SizedCollection(trashContainersTownMap[areaItem.name])
                }
            }
        }
    }

    override fun save(areaItemJson: AreaItemJson) : AreaItem {
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