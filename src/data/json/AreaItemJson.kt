package com.vcs.data.json

import com.vcs.data.base.AreaItemBase
import com.vcs.data.db.AreaItem2
import data.db.AreaItem
import org.jetbrains.exposed.sql.transactions.transaction

@Deprecated("Replaced")
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
        transaction {
            depotId = areaItem.depot?.id?.value
            trashContainerIds = areaItem.trashContainers.map { it.id.value }
            adviceIds = areaItem.advices.map { it.id.value }
        }
    }

    constructor(areaItem2: AreaItem2) : this(
        areaItem2.id.value,
        areaItem2.name,
        areaItem2.towns,
        areaItem2.separatedMulti,
        null,
        emptyMap()
    ) {
        depotId = 0
        trashContainerIds = emptyList()
        adviceIds = emptyList()
    }
}

fun AreaItem.toJson() : AreaItemJson = AreaItemJson(this)