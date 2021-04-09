package com.vcs.data.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.vcs.data.base.AreaItemBase2
import com.vcs.data.db.AreaItem2
import org.jetbrains.exposed.sql.transactions.transaction

@JsonInclude(JsonInclude.Include.NON_NULL)
class AreaItem2Json(
    val id: Int,
    override var name: String,
    override var towns: String?,
    override var separatedMulti: Boolean,
    var depotId: Int? = null,
): AreaItemBase2 {
    lateinit var trashContainerIds: List<Int>
    lateinit var adviceIds: List<Int>
    lateinit var retires: List<RetireItemInterruptionJson>

    constructor(areaItem: AreaItem2) : this(
        areaItem.id.value,
        areaItem.name,
        areaItem.towns,
        areaItem.separatedMulti,
        null
    ) {
        transaction {
            depotId = areaItem.depot?.id?.value
            trashContainerIds = areaItem.trashContainers.map { it.id.value }
            adviceIds = areaItem.advices.map { it.id.value }
            retires = areaItem.retires.map { it.toJson() }
        }
    }
}

fun AreaItem2.toJson() : AreaItem2Json = AreaItem2Json(this)