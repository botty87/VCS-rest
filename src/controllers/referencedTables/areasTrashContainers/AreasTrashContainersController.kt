package controllers.referencedTables.areasTrashContainers

import data.db.TrashContainerItem

interface AreasTrashContainersController {
    fun clear()
    fun getTrashContainersForArea(areaId: Int): List<TrashContainerItem>
    fun getAreasIdForTrashContainer(contId: Int): List<Int>
    fun setTrashContainerAndAreas(trashContainerItem: TrashContainerItem, areasId: List<Int>)
}