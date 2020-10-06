package controllers.referencedTables.areasTrashContainers

import data.db.TrashContainerItem

interface AreasTrashContainersController {
    fun clear()
    //fun addNew(areaItem: AreaItem, trashContainerItem: TrashContainerItem)
    fun getTrashContainersForArea(areaId: Int): List<TrashContainerItem>
}