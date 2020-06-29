package com.vcs.data.json

import com.vcs.data.base.RetireItemBase
import com.vcs.data.localDB.RetireItem
import java.time.LocalDate

class RetireItemJson(retireItem: RetireItem): RetireItemBase {
    val id = retireItem.id.value
    override var freq: Byte = retireItem.freq
    override var startDate: LocalDate? = retireItem.startDate
    override var time: Byte = retireItem.time
    override var type: Byte = retireItem.type
    override var name: String = retireItem.name
}