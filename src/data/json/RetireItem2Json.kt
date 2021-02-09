package com.vcs.data.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.vcs.data.base.RetireItemBase2
import com.vcs.data.db.RetireItem2
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class RetireItem2Json(
    var id: Int,
    override var type: Byte,
    override var freq: Byte,
    override var startDateTime: LocalDateTime,
    val areaId: Int? = null
) : RetireItemBase2 {

        constructor(retireItem: RetireItem2) : this(
            retireItem.id.value,
            retireItem.type,
            retireItem.freq,
            retireItem.startDateTime
        )
}

fun RetireItem2.toJson() = RetireItem2Json(this)