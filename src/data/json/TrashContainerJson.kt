package com.vcs.data.json

import com.vcs.data.base.TrashContainerBase
import com.vcs.data.localDB.TrashContainerItem

class TrashContainerJson(trashContainerItem: TrashContainerItem): TrashContainerBase {
    val id = trashContainerItem.id.value
    override var address = trashContainerItem.address
    override var latitude = trashContainerItem.latitude
    override var longitude = trashContainerItem.longitude
}