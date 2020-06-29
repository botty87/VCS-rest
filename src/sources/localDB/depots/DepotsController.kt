package com.vcs.sources.localDB.depots

import com.vcs.data.firestoreDB.DepotItemFS
import com.vcs.data.localDB.DepotItem

interface DepotsController {
    fun getAll(): List<DepotItem>
    fun clear()
    fun putFS(itemsFS: List<DepotItemFS>): Map<Int, DepotItem>
}