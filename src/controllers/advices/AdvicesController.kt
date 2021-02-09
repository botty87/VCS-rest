package com.vcs.controllers.advices

import com.vcs.data.db.AdviceItem
import com.vcs.data.json.AdviceItemJson

interface AdvicesController {
    fun getAll() : List<AdviceItem>
    fun update(adviceItemJson: AdviceItemJson) : AdviceItem
    fun createNew(adviceItemJson: AdviceItemJson) : AdviceItem
    fun delete(adviceItemJson: AdviceItemJson)
}