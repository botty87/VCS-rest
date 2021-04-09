package com.vcs.data.json

import com.vcs.data.db.InterruptionItem
import java.time.LocalDate
import java.time.LocalDateTime

class InterruptionItemJson(interruptionItem: InterruptionItem) {
    val id: Int = interruptionItem.id.value
    val whenDate: LocalDate = interruptionItem.whenDate
    val newDateTime: LocalDateTime? = interruptionItem.newDateTime
}

fun InterruptionItem.toJson() = InterruptionItemJson(this)