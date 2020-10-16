package controllers.trashContainers

import com.vcs.controllers.trashContainers.TrashContainers
import com.vcs.controllers.trashContainers.TrashContainersController
import com.vcs.data.json.TrashContainerJson
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

    override fun update(trashContainerJson: TrashContainerJson): TrashContainerItem {
        return transaction {
            TrashContainerItem[trashContainerJson.id].apply {
                address = trashContainerJson.address
                latitude = trashContainerJson.latitude
                longitude = trashContainerJson.longitude
            }
        }
    }

    override fun new(trashContainerJson: TrashContainerJson): TrashContainerItem {
        return transaction {
            TrashContainerItem.new {
                address = trashContainerJson.address
                latitude = trashContainerJson.latitude
                longitude = trashContainerJson.longitude
            }
        }
    }

    override fun delete(trashContainerJson: TrashContainerJson) {
        transaction {
            TrashContainerItem[trashContainerJson.id].delete()
        }
    }
}