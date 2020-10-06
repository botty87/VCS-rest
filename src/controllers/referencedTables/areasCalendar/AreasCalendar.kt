package controllers.referencedTables.areasCalendar

import controllers.areas.Areas
import controllers.retires.Retires
import org.jetbrains.exposed.dao.id.IntIdTable

object AreasCalendar: IntIdTable() {
    val area = reference("area", Areas).index()
    val retire = reference("retire", Retires)
    val weekDay = byte("weekDay")
    override val primaryKey = PrimaryKey(area, retire, weekDay)
}