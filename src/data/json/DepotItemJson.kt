package com.vcs.data.json

import com.vcs.data.base.DepotItemBase
import data.db.DepotItem

class DepotItemJson(depotItem: DepotItem): DepotItemBase {
    val id = depotItem.id.value
    override var address: String = depotItem.address
    override var latitude: Double = depotItem.latitude
    override var longitude: Double = depotItem.longitude
    override var name: String = depotItem.name
    override var openingHours: String = depotItem.openingHours
    override var shareDepot: Boolean = depotItem.shareDepot
}