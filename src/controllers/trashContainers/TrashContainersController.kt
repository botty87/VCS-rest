package com.vcs.controllers.trashContainers

import data.db.TrashContainerItem

interface TrashContainersController {
    fun getAll(): List<TrashContainerItem>
    fun clear()
    //fun getForArea(areaItem: AreaItem): List<TrashContainerItem>
}