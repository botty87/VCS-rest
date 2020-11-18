package com.vcs.data.db

import com.vcs.data.base.MobileAppDataBase
import com.vcs.data.dbTables.MobileAppData
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MobileAppDataItem(id: EntityID<Int>) : IntEntity(id), MobileAppDataBase {
    companion object : IntEntityClass<MobileAppDataItem>(MobileAppData)

    override var minVersion by MobileAppData.minVersion
    override var minForceVersion by MobileAppData.minForceVersion
}