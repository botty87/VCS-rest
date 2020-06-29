package com.vcs.sources.localDB.trashContainers

import com.google.common.collect.Multimap
import com.vcs.data.firestoreDB.TrashContainerFS
import com.vcs.data.localDB.TrashContainerItem

interface TrashContainersController {
    fun getAll(): List<TrashContainerItem>
    fun clear()
    fun putFS(trashContainersFS: Multimap<String, TrashContainerFS>): Multimap<String, TrashContainerItem>
}