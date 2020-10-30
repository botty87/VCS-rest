package controllers.retires

import com.vcs.data.json.RetireItemJson
import data.db.RetireItem
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZoneId

class RetiresControllerImpl: RetiresController {

    override fun getAll(): List<RetireItem> {
        return transaction {
            RetireItem.all().toList()
        }
    }

    override fun createNew(retireItemJson: RetireItemJson): RetireItem {
        val pippo = retireItemJson
        return transaction {
            RetireItem.new {
                freq = retireItemJson.freq
                time = retireItemJson.time
                type = retireItemJson.type
                name = retireItemJson.name
                startDate = retireItemJson.startDate
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