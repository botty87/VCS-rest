package com.vcs.data.db

import com.vcs.data.base.AdviceItemBase
import com.vcs.data.dbTables.Advices
import com.vcs.data.dbTables.AdvicesAreas
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AdviceItem(id: EntityID<Int>) : IntEntity(id), AdviceItemBase {
    companion object : IntEntityClass<AdviceItem>(Advices)
    override var message by Advices.message
    override var start by Advices.start
    override var end by Advices.end
    var areas by AreaItem2 via AdvicesAreas
}