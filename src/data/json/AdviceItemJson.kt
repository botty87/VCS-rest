package com.vcs.data.json

import com.vcs.data.base.AdviceItemBase
import com.vcs.data.db.AdviceItem
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class AdviceItemJson(
        val id: Int,
        override var message: String,
        override var start: LocalDate,
        override var end: LocalDate) : AdviceItemBase {

    lateinit var areas: List<Int>

    constructor(adviceItem: AdviceItem) : this(
            adviceItem.id.value,
            adviceItem.message,
            adviceItem.start,
            adviceItem.end
    ) {
        transaction {
            areas = adviceItem.areas.map { it.id.value }
        }
    }
}

fun AdviceItem.toJson(): AdviceItemJson = AdviceItemJson(this)