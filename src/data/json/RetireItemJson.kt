package com.vcs.data.json

import com.fasterxml.jackson.annotation.JsonFormat
import com.vcs.data.base.RetireItemBase
import data.db.RetireItem
import java.time.LocalDate

/*class RetireItemJson(retireItem: RetireItem?) : RetireItemBase {
    var id: Int = retireItem?.id?.value ?: 0
    override var freq: Byte = retireItem?.freq ?: 0
    override var startDate: LocalDate? = retireItem?.startDate
    override var time: Byte = retireItem?.time ?: 0
    override var type: Byte = retireItem?.type ?: 0
    override var name: String = retireItem?.name ?: ""

    constructor() : this(null) {

    }
}*/

//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
class RetireItemJson(
        var id: Int,
        override var freq: Byte,
        override var startDate: LocalDate?,
        override var time: Byte,
        override var type: Byte,
        override var name: String)  : RetireItemBase {

    constructor(retireItem: RetireItem) : this(
            retireItem.id.value,
            retireItem.freq ?: 0,
            retireItem.startDate,
            retireItem.time ?: 0,
            retireItem.type ?: 0,
            retireItem.name ?: ""
    )
}