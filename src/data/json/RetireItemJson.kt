package com.vcs.data.json

import com.vcs.data.base.RetireItemBase
import data.db.RetireItem
import java.time.LocalDate

class RetireItemJson(
        var id: Int,
        override var freq: Byte,
        override var startDate: LocalDate?,
        override var time: Byte,
        override var type: Byte,
        override var name: String)  : RetireItemBase {

    constructor(retireItem: RetireItem) : this(
            retireItem.id.value,
            retireItem.freq,
            retireItem.startDate,
            retireItem.time,
            retireItem.type,
            retireItem.name
    )
}

fun RetireItem.toJson() = RetireItemJson(this)