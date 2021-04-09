package com.vcs.data.db

import com.vcs.data.dbTables.InterruptionRetires
import com.vcs.data.dbTables.Interruptions
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class InterruptionItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<InterruptionItem>(Interruptions)
    var retires by RetireItem2 via InterruptionRetires
    var whenDate by Interruptions.whenDate
    var newDateTime by Interruptions.newDateTime
}