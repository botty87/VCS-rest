package com.vcs.controllers.advices

import com.vcs.data.db.AdviceItem
import com.vcs.data.db.AreaItem2
import com.vcs.data.dbTables.Advices
import com.vcs.data.json.AdviceItemJson
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import java.time.LocalDate

class AdvicesControllerImpl: AdvicesController, KoinComponent {
    override fun getAll(): List<AdviceItem> {
        return transaction {
            val advices = AdviceItem.all().toMutableList()
            val now = LocalDate.now()
            advices.removeIf {
                val remove = it.end.isBefore(now)
                if(remove) {
                    it.delete()
                }
                remove
            }
            advices
        }
    }

    override fun update(adviceItemJson: AdviceItemJson): AdviceItem {
        return transaction {
            AdviceItem[adviceItemJson.id].apply {
                message = adviceItemJson.message
                start = adviceItemJson.start
                end = adviceItemJson.end
                areas = AreaItem2.forIds(adviceItemJson.areas)
            }
        }
    }

    override fun createNew(adviceItemJson: AdviceItemJson): AdviceItem {
        val advice = transaction {
            AdviceItem.new {
                message = adviceItemJson.message
                start = adviceItemJson.start
                end = adviceItemJson.end
            }
        }
        transaction {
            advice.areas = AreaItem2.forIds(adviceItemJson.areas)
        }

        return advice
    }

    override fun delete(adviceItemJson: AdviceItemJson) {
        transaction {
            Advices.deleteWhere { Advices.id eq adviceItemJson.id }
        }
    }
}