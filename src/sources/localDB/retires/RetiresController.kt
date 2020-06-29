package com.vcs.sources.localDB.retires

import com.vcs.data.firestoreDB.RetireItemFS
import com.vcs.data.localDB.RetireItem

interface RetiresController {
    fun getAll(): List<RetireItem>
    fun clear()
    fun putFS(itemsFS: List<RetireItemFS>)
}