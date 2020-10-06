package com.vcs.data.base

import java.time.LocalDate

interface RetireItemBase {
    var freq: Byte
    var startDate: LocalDate?
    var time: Byte
    var type: Byte
    var name: String

    companion object{
        const val PAPER: Byte = 1
        const val WASTE: Byte = 2
        const val MULTI: Byte = 3
        const val ORGANIC: Byte = 4
        const val PLASTIC: Byte = 5
        const val GLASS: Byte = 6
    }
}