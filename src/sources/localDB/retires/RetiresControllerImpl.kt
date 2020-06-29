package com.vcs.sources.localDB.retires

import com.vcs.data.base.RetireItemBase
import com.vcs.data.firestoreDB.RetireItemFS
import com.vcs.data.localDB.RetireItem
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZoneId

class RetiresControllerImpl: RetiresController {
    private val zone by lazy {
        ZoneId.getAvailableZoneIds().first {
            it.contains("rome", true) || it.contains("italy", true)
        }
    }

    override fun getAll(): List<RetireItem> {
        return transaction {
            RetireItem.all().toList()
        }
    }

    override fun clear() {
        transaction {
            Retires.deleteAll()
        }
    }

    override fun putFS(itemsFS: List<RetireItemFS>) {
        fun RetireItemFS.getTypeID(): Byte {
            return when(type) {
                "car" -> RetireItemBase.PAPER
                "mul" -> RetireItemBase.MULTI
                "pla" -> RetireItemBase.PLASTIC
                "vet" -> RetireItemBase.GLASS
                "umi" -> RetireItemBase.ORGANIC
                else -> RetireItemBase.WASTE
            }
        }

        transaction {
            itemsFS.forEach { item ->
                RetireItem.new {
                    freq = item.freq.toByte()
                    time = item.time.toByte()
                    type = item.getTypeID()
                    name = item.name
                    startDate = item.startDate?.toDate()?.toInstant()?.atZone(ZoneId.of(zone))?.toLocalDate()
                }
            }
        }
    }
}