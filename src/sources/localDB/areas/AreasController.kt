package com.vcs.sources.localDB.areas

import com.google.common.collect.Multimap
import com.vcs.data.firestoreDB.AreaItemFS
import com.vcs.data.json.AreaItemJson
import com.vcs.data.localDB.AreaItem
import com.vcs.data.localDB.DepotItem
import com.vcs.data.localDB.TrashContainerItem

interface AreasController {
    fun getAll(): List<AreaItem>
    fun clear()
    fun putFS(areaItemsFS: List<AreaItemFS>, depotItemsMap: Map<Int, DepotItem>, trashContainersTownMap: Multimap<String, TrashContainerItem>)
    fun save(areaItemJson: AreaItemJson) : AreaItem
}