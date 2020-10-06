package controllers.areas

import com.vcs.data.json.AreaItemJson
import data.db.AreaItem

interface AreasController {
    fun getAll(): List<AreaItem>
    fun clear()
    fun update(areaItemJson: AreaItemJson) : AreaItem
}