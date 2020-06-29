package com.vcs.sources.localDB.referencedTables.areasTrashContainers

import com.vcs.sources.localDB.areas.Areas
import com.vcs.sources.localDB.trashContainers.TrashContainers
import org.jetbrains.exposed.sql.Table

object AreasTrashContainers: Table() {
    val area = reference("area", Areas)
    val trashContainer = reference("trash_container", TrashContainers)
    override val primaryKey = PrimaryKey(area, trashContainer)
}