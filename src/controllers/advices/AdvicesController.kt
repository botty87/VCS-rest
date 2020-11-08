package com.vcs.controllers.advices

import com.vcs.data.db.AdviceItem

interface AdvicesController {
    fun getAll() : List<AdviceItem>
}