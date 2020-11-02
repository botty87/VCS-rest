package com.vcs.data.json

import com.vcs.data.base.DepotItemBase
import data.db.DepotItem

class DepotItemJson(
        val id: Int,
        override var address: String,
        override var latitude: Double,
        override var longitude: Double,
        override var name: String,
        override var openingHours: String,
        override var shareDepot: Boolean): DepotItemBase {

    constructor(depotItem: DepotItem) : this(
            depotItem.id.value,
            depotItem.address,
            depotItem.latitude,
            depotItem.longitude,
            depotItem.name,
            depotItem.openingHours,
            depotItem.shareDepot,
    )
}