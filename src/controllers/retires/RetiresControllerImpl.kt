package controllers.retires

import com.vcs.controllers.areas2.Areas2Controller
import com.vcs.data.db.RetireItem2
import com.vcs.data.json.RetireItem2Json
import com.vcs.data.json.RetireItemJson
import data.db.RetireItem
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class RetiresControllerImpl: RetiresController, KoinComponent {

    override fun getAll(): List<RetireItem> {
        return transaction {
            RetireItem.all().toList()
        }
    }

    override fun createNew(retireItemJson: RetireItem2Json): RetireItem2 {
        val areas2Controller: Areas2Controller by inject()

        return transaction {
            val area = areas2Controller.getArea(retireItemJson.areaId!!, false)
            RetireItem2.new {
                freq = retireItemJson.freq
                type = retireItemJson.type
                startDateTime = retireItemJson.startDateTime
                this.area = area
            }
        }
    }

    override fun update(retireItemJson: RetireItemJson): RetireItem {
        return transaction {
            RetireItem[retireItemJson.id].apply {
                freq = retireItemJson.freq
                time = retireItemJson.time
                type = retireItemJson.type
                name = retireItemJson.name
                startDate = retireItemJson.startDate
            }
        }
    }

    override fun delete(retireItemJson: RetireItemJson) {
        transaction {
            RetireItem[retireItemJson.id].delete()
        }
    }
}