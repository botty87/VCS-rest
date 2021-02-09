package com.vcs.data.dbTables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object AreasTrashContainers: Table() {
    val area = reference("area", Areas2)
    val trashContainer = reference("trash_container", TrashContainers, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(area, trashContainer)
}