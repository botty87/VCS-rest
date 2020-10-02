package com.vcs.sources.localDB.referencedTables.areasTrashContainers

import com.vcs.data.localDB.AreaItem
import com.vcs.data.localDB.TrashContainerItem

interface AreasTrashContainersController {
    fun clear()
    //fun addNew(areaItem: AreaItem, trashContainerItem: TrashContainerItem)
    fun getTrashContainersForArea(): List<TrashContainerItem>
}