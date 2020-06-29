package com.vcs.data.localDB

import com.vcs.data.base.AreaItemBase
import com.vcs.sources.localDB.areas.Areas
import com.vcs.sources.localDB.referencedTables.areasCalendar.AreasCalendarController
import com.vcs.sources.localDB.referencedTables.areasTrashContainers.AreasTrashContainers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.get

class AreaItem(id: EntityID<Int>) : IntEntity(id), AreaItemBase, KoinComponent {
    companion object : IntEntityClass<AreaItem>(Areas)
    override var name by Areas.name
    override var towns by Areas.towns
    var depot by DepotItem optionalReferencedOn Areas.depot
    var trashContainers by TrashContainerItem via AreasTrashContainers

    val calendarMap: Map<Byte, Collection<Int>>
        get() = transaction {
            val areasCalendarController: AreasCalendarController = get()
            areasCalendarController.getRetiresForArea(this@AreaItem).asMap()
        }
}