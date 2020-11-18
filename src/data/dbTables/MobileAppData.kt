package com.vcs.data.dbTables

import com.vcs.data.base.MobileAppDataBase.Companion.UNIQUE_ID
import org.jetbrains.exposed.dao.id.IdTable

object MobileAppData: IdTable<Int>() {
    override val id = integer("id").default(UNIQUE_ID).entityId()
    val minVersion = integer("minVersion").default(0)
    val minForceVersion = integer("minForceVersion").default(0)
}