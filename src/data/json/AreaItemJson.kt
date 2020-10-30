package com.vcs.data.json

import com.vcs.data.base.AreaItemBase
import data.db.AreaItem
import org.jetbrains.exposed.sql.transactions.transaction

/*class AreaItemJson(areaItem: AreaItem): AreaItemBase {
    val id = areaItem.id.value
    override var name: String = areaItem.name
    override var towns: String? = areaItem.towns
    var depotId: Int? = null
    var calendarMap: Map<Byte, Collection<Int>> = areaItem.calendarMap
    lateinit var trashContainerIds: List<Int>

    init {
        transaction {
            depotId = areaItem.depot?.id?.value
            trashContainerIds = areaItem.trashContainers.map { it.id.value }
        }
    }
}*/

class AreaItemJson(
        val id: Int,
        override var name: String,
        override var towns: String?,
        var depotId: Int? = null,
        var calendarMap: Map<Byte, Collection<Int>>,
) : AreaItemBase {
    lateinit var trashContainerIds: List<Int>

    constructor(areaItem: AreaItem) : this(
        areaItem.id.value,
        areaItem.name,
        areaItem.towns,
            null,
        areaItem.calendarMap
    ) {
        transaction {
            depotId = areaItem.depot?.id?.value
            trashContainerIds = areaItem.trashContainers.map { it.id.value }
        }
    }
}