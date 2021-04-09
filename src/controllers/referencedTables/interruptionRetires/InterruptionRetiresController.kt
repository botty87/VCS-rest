package com.vcs.controllers.referencedTables.interruptionRetires

import org.jetbrains.exposed.dao.id.EntityID

interface InterruptionRetiresController {
    fun getInterruptionIdsForRetire(retireItemId: Int) : List<EntityID<Int>>
    fun containInterruptionId(interruptionId: EntityID<Int>) : Boolean
}