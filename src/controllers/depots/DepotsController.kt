package controllers.depots

import com.vcs.data.json.DepotItemJson
import data.db.DepotItem

interface DepotsController {
    fun getAll(): List<DepotItem>
    fun update(depotItemJson: DepotItemJson): DepotItem
    fun createNew(depotItemJson: DepotItemJson): DepotItem
    fun delete(depotItemJson: DepotItemJson)
}