package com.vcs.controllers.advices

import com.vcs.data.db.AdviceItem
import org.jetbrains.exposed.sql.transactions.transaction

class AdvicesControllerImpl: AdvicesController {
    override fun getAll(): List<AdviceItem> {
        return transaction {
            AdviceItem.all().toList()
        }
    }
}