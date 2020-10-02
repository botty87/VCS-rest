package com.vcs.sources.localDB.depots

import com.vcs.data.json.DepotItemJson
import com.vcs.data.localDB.DepotItem
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class DepotsControllerImp: DepotsController {
    override fun getAll(): List<DepotItem> {
        return transaction {
            DepotItem.all().toList()
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