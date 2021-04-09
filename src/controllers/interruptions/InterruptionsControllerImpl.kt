package com.vcs.controllers.interruptions

import com.vcs.data.db.InterruptionItem
import com.vcs.data.db.RetireItem2
import com.vcs.data.dbTables.Interruptions
import com.vcs.data.json.NewInterruptionItemJson
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent

class InterruptionsControllerImpl: InterruptionsController, KoinComponent {
    override fun getAll(): List<InterruptionItem> {
        return transaction {
            InterruptionItem.all().toList()
        }
    }

    override fun add(newInterruptionItemJson: NewInterruptionItemJson): InterruptionItem {
        return transaction {
            InterruptionItem.new {
                whenDate = newInterruptionItemJson.whenDate
                newDateTime = newInterruptionItemJson.newDateTime
            }.apply {
                this.retires = RetireItem2.forIds(newInterruptionItemJson.retireIds)
            }
        }
    }

    override fun delete(interruptionId: Int, requireTransaction: Boolean) {
        if(requireTransaction)
            transaction {
                Interruptions.deleteWhere { Interruptions.id eq interruptionId }
            }
        else
            Interruptions.deleteWhere { Interruptions.id eq interruptionId }
    }
}