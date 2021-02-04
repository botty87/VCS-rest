package controllers.retires

import com.vcs.data.db.RetireItem2
import com.vcs.data.json.RetireItem2Json
import com.vcs.data.json.RetireItemJson
import data.db.RetireItem

interface RetiresController {
    fun getAll(): List<RetireItem>
    fun createNew(retireItemJson: RetireItem2Json): RetireItem2
    fun update(retireItemJson: RetireItemJson): RetireItem
    fun delete(retireItemJson: RetireItemJson)
}