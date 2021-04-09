package com.vcs.data.json

import java.time.LocalDate
import java.time.LocalDateTime

class NewInterruptionItemJson(
    val retireIds: List<Int>,
    val whenDate: LocalDate,
    val newDateTime: LocalDateTime?
)