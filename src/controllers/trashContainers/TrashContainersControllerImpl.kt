package controllers.trashContainers

import com.vcs.controllers.trashContainers.TrashContainers
import com.vcs.controllers.trashContainers.TrashContainersController
import data.db.TrashContainerItem
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