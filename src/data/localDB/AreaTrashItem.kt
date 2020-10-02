package com.vcs.data.localDB

import com.vcs.sources.localDB.depots.Depots
import com.vcs.sources.localDB.referencedTables.areasTrashContainersNew.AreasTrashContainersNew
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AreaTrashItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AreaTrashItem>(Depots)
    var area by AreaItem referencedOn AreasTrashContainersNew.area
    var trashContainerItem by TrashContainerItem referencedOn AreasTrashContainersNew.trashContainer
}