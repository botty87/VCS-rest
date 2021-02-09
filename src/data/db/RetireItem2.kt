package com.vcs.data.db

import com.vcs.data.base.RetireItemBase2
import com.vcs.data.dbTables.Retires2
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RetireItem2(id: EntityID<Int>) : IntEntity(id), RetireItemBase2 {
    companion object : IntEntityClass<RetireItem2>(Retires2)
    override var freq by Retires2.freq
    override var type by Retires2.type
    override var startDateTime by Retires2.startDateTime
    var area by AreaItem2 referencedOn Retires2.area
}