package com.vcs.data.dbTables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object AreasRetires: Table() {
    val area = reference("area", Areas2)
    val retire = reference("retires", Retires2, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(area, retire)
}