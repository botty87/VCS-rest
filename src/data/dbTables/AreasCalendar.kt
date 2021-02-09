package com.vcs.data.dbTables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object AreasCalendar: IntIdTable() {
    val area = reference("area", Areas, onDelete = ReferenceOption.CASCADE).index()
    val retire = reference("retire", Retires, onDelete = ReferenceOption.CASCADE)
    val weekDay = byte("weekDay")
    override val primaryKey = PrimaryKey(area, retire, weekDay)
}