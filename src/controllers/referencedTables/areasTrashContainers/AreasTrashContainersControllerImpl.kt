package controllers.referencedTables.areasTrashContainers

import data.db.TrashContainerItem
import controllers.areas.Areas
import data.db.AreaTrashItem
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

    override fun getTrashContainersForArea(areaId: Int): List<TrashContainerItem> {
        return transaction {
            val retiresIds = AreasTrashContainers.select {
                AreasTrashContainers.area eq areaId
            }.map { it[AreasTrashContainers.trashContainer] }

            TrashContainerItem.forEntityIds(retiresIds).toList()
        }
    }
}