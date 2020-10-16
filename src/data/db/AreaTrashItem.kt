package data.db

import controllers.depots.Depots
import controllers.referencedTables.areasTrashContainers.AreasTrashContainers
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AreaTrashItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AreaTrashItem>(Depots)
    var area by AreaItem referencedOn AreasTrashContainers.area
    var trashContainerItem by TrashContainerItem referencedOn AreasTrashContainers.trashContainer
}