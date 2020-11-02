package com.vcs.data.base

import java.time.LocalDate

interface RetireItemBase {
    var freq: Byte
    var startDate: LocalDate?
    var time: Byte
    var type: Byte
    var name: String
}