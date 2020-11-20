package data.db

import com.vcs.data.base.AreaItemBase
import com.vcs.data.db.AdviceItem
import com.vcs.data.dbTables.AdvicesAreas
import com.vcs.data.dbTables.Areas
import com.vcs.data.dbTables.AreasTrashContainers
import controllers.referencedTables.areasCalendar.AreasCalendarController
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
    override var separatedMulti by Areas.separatedMulti
    var depot by DepotItem optionalReferencedOn Areas.depot
    var trashContainers by TrashContainerItem via AreasTrashContainers
    var advices by AdviceItem via AdvicesAreas

    val calendarMap: Map<Byte, Collection<Int>>
        get() = transaction {
            val areasCalendarController: AreasCalendarController = get()
            areasCalendarController.getRetiresForArea(this@AreaItem).asMap()
        }
}