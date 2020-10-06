package controllers.retires

import com.vcs.data.json.RetireItemJson
import data.db.RetireItem

interface RetiresController {
    fun getAll(): List<RetireItem>
    fun createNew(retireItemJson: RetireItemJson): RetireItem
    fun update(retireItemJson: RetireItemJson): RetireItem
    fun delete(retireItemJson: RetireItemJson)
}