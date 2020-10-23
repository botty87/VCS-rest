package controllers.referencedTables.areasTrashContainers

import com.vcs.data.dbTables.AreasTrashContainers
import data.db.TrashContainerItem
import com.vcs.data.dbTables.Areas
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
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

    override fun getAreasIdForTrashContainer(contId: Int): List<Int> {
        return transaction {
            AreasTrashContainers.select {
                AreasTrashContainers.trashContainer eq contId
            }.map { it[AreasTrashContainers.area].value }
        }
    }

    override fun setTrashContainerAndAreas(trashContainerItem: TrashContainerItem, areasId: List<Int>) {
        transaction {
            AreasTrashContainers.deleteWhere {
                (AreasTrashContainers.trashContainer eq trashContainerItem.id)
            }
            AreasTrashContainers.batchInsert(areasId, shouldReturnGeneratedValues = false) { areaId ->
                this[AreasTrashContainers.trashContainer] = trashContainerItem.id
                this[AreasTrashContainers.area] = EntityID(areaId, Areas)
            }
        }
    }
}