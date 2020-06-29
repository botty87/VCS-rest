package com.vcs.data.localDB

import com.vcs.data.base.DepotItemBase
import com.vcs.sources.localDB.depots.Depots
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DepotItem(id: EntityID<Int>) : IntEntity(id), DepotItemBase {
    companion object : IntEntityClass<DepotItem>(Depots)
    override var address: String by Depots.address
    override var latitude: Double by Depots.latitude
    override var longitude: Double by Depots.longitude
    override var name: String by Depots.name
    override var openingHours: String by Depots.openingHours
    override var shareDepot: Boolean by Depots.shareDepot
}