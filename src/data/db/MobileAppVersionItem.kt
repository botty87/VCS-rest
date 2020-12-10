package com.vcs.data.db

import com.vcs.data.base.MobileAppVersionBase
import com.vcs.data.dbTables.MobileAppVersion
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MobileAppVersionItem(id: EntityID<Int>) : IntEntity(id), MobileAppVersionBase {
    companion object : IntEntityClass<MobileAppVersionItem>(MobileAppVersion)

    override var currentVersion by MobileAppVersion.currentVersion
    override var minForceVersion by MobileAppVersion.minForceVersion
    override var itunesVersion: String? = null
}