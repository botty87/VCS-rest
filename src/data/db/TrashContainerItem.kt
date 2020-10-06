package data.db

import com.vcs.data.base.TrashContainerBase
import com.vcs.controllers.trashContainers.TrashContainers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TrashContainerItem(id: EntityID<Int>) : IntEntity(id), TrashContainerBase {
    companion object : IntEntityClass<TrashContainerItem>(TrashContainers)
    override var address: String by TrashContainers.address
    override var latitude: Double by TrashContainers.latitude
    override var longitude: Double by TrashContainers.longitude
}