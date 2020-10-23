package data.db

import com.vcs.data.dbTables.Depots
import com.vcs.data.dbTables.AreasTrashContainers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AreaTrashItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AreaTrashItem>(Depots)
    var area by AreaItem referencedOn AreasTrashContainers.area
    var trashContainerItem by TrashContainerItem referencedOn AreasTrashContainers.trashContainer
}