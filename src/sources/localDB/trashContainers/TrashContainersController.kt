package com.vcs.sources.localDB.trashContainers

import com.google.common.collect.Multimap
import com.vcs.data.localDB.AreaItem
import com.vcs.data.localDB.TrashContainerItem

interface TrashContainersController {
    fun getAll(): List<TrashContainerItem>
    fun clear()
    //fun getForArea(areaItem: AreaItem): List<TrashContainerItem>
}