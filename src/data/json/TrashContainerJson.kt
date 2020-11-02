package com.vcs.data.json

import com.vcs.data.base.TrashContainerBase
import data.db.TrashContainerItem

class TrashContainerJson(
        val id: Int,
        override var address: String,
        override var latitude: Double,
        override var longitude: Double) : TrashContainerBase {

    constructor(trashContainerItem: TrashContainerItem) : this(
            trashContainerItem.id.value,
            trashContainerItem.address,
            trashContainerItem.latitude,
            trashContainerItem.longitude,
    )
}