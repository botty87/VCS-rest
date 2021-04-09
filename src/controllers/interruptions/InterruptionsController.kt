package com.vcs.controllers.interruptions

import com.vcs.data.db.InterruptionItem
import com.vcs.data.json.NewInterruptionItemJson

interface InterruptionsController {
    fun getAll(): List<InterruptionItem>
    fun add(newInterruptionItemJson: NewInterruptionItemJson) : InterruptionItem
    fun delete(interruptionId: Int, requireTransaction: Boolean = true)
}