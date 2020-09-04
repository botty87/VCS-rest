package com.vcs.sources.localDB.retires

import com.vcs.data.json.RetireItemJson
import com.vcs.data.localDB.RetireItem

interface RetiresController {
    fun getAll(): List<RetireItem>
    fun createNew(retireItemJson: RetireItemJson): RetireItem
    fun update(retireItemJson: RetireItemJson): RetireItem
    fun delete(retireItemJson: RetireItemJson)
}