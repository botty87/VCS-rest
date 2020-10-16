package controllers.referencedTables.areasTrashContainers

import controllers.areas.Areas
import com.vcs.controllers.trashContainers.TrashContainers
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object AreasTrashContainers: Table() {
    val area = reference("area", Areas)
    val trashContainer = reference("trash_container", TrashContainers, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(area, trashContainer)
}