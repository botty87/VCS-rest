package com.vcs.data.dbTables

import com.vcs.data.base.MobileAppVersionBase.Companion.UNIQUE_ID
import org.jetbrains.exposed.dao.id.IdTable

object MobileAppVersion: IdTable<Int>() {
    override val id = integer("id").default(UNIQUE_ID).entityId()
    override val primaryKey = PrimaryKey(id)

    val currentVersion = integer("currentVersion").default(0)
    val minForceVersion = integer("minForceVersion").default(0)
}