package com.vcs.sources.localDB.areas

import com.vcs.data.json.AreaItemJson
import com.vcs.data.localDB.AreaItem

interface AreasController {
    fun getAll(): List<AreaItem>
    fun clear()
    fun update(areaItemJson: AreaItemJson) : AreaItem
}