package com.vcs.controllers.trashContainers

import com.vcs.data.json.TrashContainerJson
import data.db.TrashContainerItem

interface TrashContainersController {
    fun getAll(): List<TrashContainerItem>
    fun clear()
    fun update(trashContainerJson: TrashContainerJson) : TrashContainerItem
    fun new(trashContainerJson: TrashContainerJson) : TrashContainerItem
    fun delete(trashContainerJson: TrashContainerJson)
}