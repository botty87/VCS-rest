package com.vcs.sources.localDB.retires

import com.vcs.data.PostResult
import com.vcs.data.json.RetireItemJson
import com.vcs.data.localDB.RetireItem
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZoneId

class RetiresControllerImpl: RetiresController {
    private val zone by lazy {
        ZoneId.getAvailableZoneIds().first {
            it.contains("rome", true) || it.contains("italy", true)
        }
    }

    override fun getAll(): List<RetireItem> {
        return transaction {
            RetireItem.all().toList()
        }
    }

    override fun createNew(retireItemJson: RetireItemJson): RetireItem {
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