package com.vcs.sources.localDB.referencedTables.areasTrashContainers

import com.vcs.data.localDB.AreaItem
import com.vcs.data.localDB.TrashContainerItem
import com.vcs.sources.localDB.areas.Areas
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class AreasTrashContainersControllerImpl: AreasTrashContainersController {
    override fun clear() {
        transaction {
            AreasTrashContainers.deleteAll()
        }
    }

    /*override fun addNew(areaItem: AreaItem, trashContainerItem: TrashContainerItem) {
        AreaTrashContainerItem.new {
            area = areaItem
            trashContainer = trashContainerItem
        }
    }*/

    override fun getTrashContainersForArea(): List<TrashContainerItem> {
        transaction {
            AreasTrashContainers.selectAll().forEach {
                val pippo = it
                val pluto = pippo
            }

            val test = AreasTrashContainers.select {
                Areas.id eq 44
            }

            test.forEach{
                val pippo = it
                val pluto = pippo
            }
        }
        return emptyList()
    }
}