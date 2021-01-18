package com.vcs.data.json

import com.vcs.data.base.AreaItemBase
import data.db.AreaItem
import org.jetbrains.exposed.sql.transactions.transaction

//TODO check constructor
class AreaItemJson(
        val id: Int,
        override var name: String,
        override var towns: String?,
        override var separatedMulti: Boolean,
        var depotId: Int? = null,
        var calendarMap: Map<Byte, Collection<Int>>,
) : AreaItemBase {
    lateinit var trashContainerIds: List<Int>
    lateinit var adviceIds: List<Int>

    constructor(areaItem: AreaItem) : this(
        areaItem.id.value,
        areaItem.name,
        areaItem.towns,
        areaItem.separatedMulti,
        null,
        areaItem.calendarMap
    ) {
        var pippo : List<Int>;
        transaction {
            depotId = areaItem.depot?.id?.value
            trashContainerIds = areaItem.trashContainers.map { it.id.value }
            adviceIds = areaItem.advices.map { it.id.value }
        }
    }
}

fun AreaItem.toJson() : AreaItemJson = AreaItemJson(this)