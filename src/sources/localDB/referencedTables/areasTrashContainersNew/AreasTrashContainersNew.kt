package com.vcs.sources.localDB.referencedTables.areasTrashContainersNew

import com.vcs.sources.localDB.areas.Areas
import com.vcs.sources.localDB.trashContainers.TrashContainers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object AreasTrashContainersNew: IntIdTable() {
    val area = reference("area", Areas)
    val trashContainer = reference("trash_container", TrashContainers)
    override val primaryKey = PrimaryKey(area, trashContainer)
}