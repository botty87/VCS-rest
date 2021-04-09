package com.vcs.controllers.referencedTables.interruptionRetires

import com.vcs.data.dbTables.InterruptionRetires
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.select

class InterruptionRetiresControllerImpl: InterruptionRetiresController {
    override fun getInterruptionIdsForRetire(retireItemId: Int): List<EntityID<Int>> {
        return InterruptionRetires.select { InterruptionRetires.retire eq retireItemId }.map {
            it[InterruptionRetires.interruption]
        }
    }

    override fun containInterruptionId(interruptionId: EntityID<Int>): Boolean {
        return !InterruptionRetires.select { InterruptionRetires.interruption eq interruptionId }
            .empty()
    }

}