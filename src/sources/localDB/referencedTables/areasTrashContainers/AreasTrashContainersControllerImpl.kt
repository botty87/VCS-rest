package com.vcs.sources.localDB.referencedTables.areasTrashContainers

import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class AreasTrashContainersControllerImpl: AreasTrashContainersController {
    override fun clear() {
        transaction {
            AreasTrashContainers.deleteAll()
        }
    }

    /*override fun addNew(areaItem: AreaItem, trashContainerItem: TrashContainerItem) {
        AreaTrashContainerItem.new {
            area = areaItem
            trashContainer = trashContainerItem
        }
    }*/

    /*override fun getTrashContainersForArea(areaItem: AreaItem): List<TrashContainerItem> {
        return emptyList()
    }*/
}