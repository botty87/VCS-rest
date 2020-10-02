package com.vcs.sources.localDB.referencedTables.areasTrashContainersNew

import com.vcs.data.localDB.AreaItem
import com.vcs.data.localDB.AreaTrashItem
import com.vcs.data.localDB.TrashContainerItem
import com.vcs.sources.localDB.areas.Areas
import com.vcs.sources.localDB.referencedTables.areasTrashContainers.AreasTrashContainers
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class AreasTrashContainersControllerImplNew: AreasTrashContainersControllerNew {
    override fun move() {
        transaction {
            AreasTrashContainers.selectAll().forEach {
                val areaId = it[AreasTrashContainers.area]
                val areaIt = AreaItem[areaId]
                val trashContainerId = it[AreasTrashContainers.trashContainer]
                val trashContainerIt = TrashContainerItem[trashContainerId]
                AreaTrashItem.new {
                    area = areaIt
                    trashContainerItem = trashContainerIt
                }
            }
        }
    }
}