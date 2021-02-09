package controllers.depots

import com.vcs.data.json.DepotItemJson
import data.db.DepotItem
import org.jetbrains.exposed.sql.transactions.transaction

class DepotsControllerImp: DepotsController {
    override fun getAll(): List<DepotItem> {
        return transaction {
            DepotItem.all().sortedBy { it.name.toLowerCase() }
        }
    }

    override fun update(depotItemJson: DepotItemJson): DepotItem {
        return transaction {
            DepotItem[depotItemJson.id].apply {
                address = depotItemJson.address
                latitude = depotItemJson.latitude
                longitude = depotItemJson.longitude
                name = depotItemJson.name
                openingHours = depotItemJson.openingHours
                shareDepot = depotItemJson.shareDepot
            }
        }
    }

    override fun createNew(depotItemJson: DepotItemJson): DepotItem {
        return transaction {
            DepotItem.new {
                address = depotItemJson.address
                latitude = depotItemJson.latitude
                longitude = depotItemJson.longitude
                name = depotItemJson.name
                openingHours = depotItemJson.openingHours
                shareDepot = depotItemJson.shareDepot
            }
        }
    }

    override fun delete(depotItemJson: DepotItemJson) {
        transaction {
            DepotItem[depotItemJson.id].delete()
        }
    }
}