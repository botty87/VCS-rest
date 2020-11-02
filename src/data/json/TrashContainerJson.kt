package com.vcs.data.json

import com.vcs.data.base.TrashContainerBase
import data.db.TrashContainerItem

/*class TrashContainerJson(trashContainerItem: TrashContainerItem): TrashContainerBase {
    val id = trashContainerItem.id.value
    override var address = trashContainerItem.address,
    override var latitude = trashContainerItem.latitude,
    override var longitude = trashContainerItem.longitude,
}*/

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