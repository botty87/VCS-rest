package com.vcs.sources.localDB.trashContainers

import com.vcs.data.localDB.AreaItem
import com.vcs.data.localDB.TrashContainerItem
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class TrashContainersControllerImpl: TrashContainersController {
    override fun getAll(): List<TrashContainerItem> {
        return transaction {
            TrashContainerItem.all().toList()
        }
    }

    override fun clear() {
        transaction {
            TrashContainers.deleteAll()
        }
    }

    /*override fun getForArea(areaItem: AreaItem): List<TrashContainerItem> {
        transaction {

        }
    }*/
}