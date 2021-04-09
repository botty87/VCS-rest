package com.vcs.controllers.areas2

import com.vcs.data.db.AreaItem2

interface Areas2Controller {
    fun getAll(): List<AreaItem2>
    fun getArea(id: Int, requireTransaction: Boolean = true): AreaItem2
    fun migrate()
}