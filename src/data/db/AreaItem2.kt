package com.vcs.data.db

import com.vcs.data.base.AreaItemBase2
import com.vcs.data.dbTables.AdvicesAreas
import com.vcs.data.dbTables.Areas2
import com.vcs.data.dbTables.AreasRetires
import com.vcs.data.dbTables.AreasTrashContainers
import data.db.DepotItem
import data.db.TrashContainerItem
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AreaItem2(id: EntityID<Int>) : IntEntity(id), AreaItemBase2 {
    companion object : IntEntityClass<AreaItem2>(Areas2)
    override var name by Areas2.name
    override var towns by Areas2.towns
    override var separatedMulti by Areas2.separatedMulti
    var depot by DepotItem optionalReferencedOn Areas2.depot
    var trashContainers by TrashContainerItem via AreasTrashContainers
    var advices by AdviceItem via AdvicesAreas
    var retires by RetireItem2 via AreasRetires
}