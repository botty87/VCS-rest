package controllers.areas

import com.vcs.data.json.AreaItemJson
import data.db.AreaItem

@Deprecated("Replaced")
interface AreasController {
    fun getAll(): List<AreaItem>
    fun clear()
    fun update(areaItemJson: AreaItemJson) : AreaItem
}