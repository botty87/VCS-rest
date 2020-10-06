package com.vcs.data.json

import com.vcs.data.base.RetireItemBase
import data.db.RetireItem
import java.time.LocalDate

class RetireItemJson(retireItem: RetireItem?) : RetireItemBase {
    var id: Int = retireItem?.id?.value ?: 0
    override var freq: Byte = retireItem?.freq ?: 0
    override var startDate: LocalDate? = retireItem?.startDate
    override var time: Byte = retireItem?.time ?: 0
    override var type: Byte = retireItem?.type ?: 0
    override var name: String = retireItem?.name ?: ""
}