package com.vcs.sources.localDB.depots

import com.vcs.data.json.DepotItemJson
import com.vcs.data.localDB.DepotItem

interface DepotsController {
    fun getAll(): List<DepotItem>
    fun update(depotItemJson: DepotItemJson): DepotItem
    fun createNew(depotItemJson: DepotItemJson): DepotItem
    fun delete(depotItemJson: DepotItemJson)
}