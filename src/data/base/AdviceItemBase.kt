package com.vcs.data.base

import java.time.LocalDate

interface AdviceItemBase {
    var message: String
    var start: LocalDate
    var end: LocalDate
}