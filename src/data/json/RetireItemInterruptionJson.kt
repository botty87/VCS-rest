package com.vcs.data.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.vcs.data.db.RetireItem2
import org.jetbrains.exposed.sql.transactions.transaction

@JsonInclude(JsonInclude.Include.NON_NULL)
class RetireItemInterruptionJson(
    retireItem: RetireItem2) : RetireItem2Json(retireItem) {
    val interruptions: List<InterruptionItemJson>? = transaction {
        retireItem.interruptions.map { it.toJson() }.ifEmpty { null }
    }
}

fun RetireItem2.toJson() = RetireItemInterruptionJson(this)