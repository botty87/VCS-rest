package controllers.retires

import com.vcs.data.db.RetireItem2
import com.vcs.data.json.RetireItem2Json
import data.db.RetireItem

interface RetiresController {
    fun getAll(): List<RetireItem>
    fun getRetire(id: Int, requireTransaction: Boolean = true): RetireItem2
    fun createNew(retireItemJson: RetireItem2Json): RetireItem2
    fun delete(retireItemId: Int)
}