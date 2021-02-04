package data.db

import com.vcs.data.base.RetireItemBase
import com.vcs.data.dbTables.Retires
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDate

@Deprecated("Replaced")
class RetireItem(id: EntityID<Int>) : IntEntity(id), RetireItemBase {
    companion object : IntEntityClass<RetireItem>(Retires)
    override var freq: Byte by Retires.freq
    override var time: Byte by Retires.time
    override var type: Byte by Retires.type
    override var name: String by Retires.name
    override var startDate: LocalDate? by Retires.startDate
}