package com.vcs.sources.localDB.depots

import com.vcs.data.firestoreDB.DepotItemFS
import com.vcs.data.localDB.DepotItem
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class DepotsControllerImp: DepotsController {
    override fun getAll(): List<DepotItem> {
        return transaction {
            DepotItem.all().toList()
        }
    }

    override fun clear() {
        transaction {
            Depots.deleteAll()
        }
    }

    override fun putFS(itemsFS: List<DepotItemFS>): Map<Int, DepotItem> {
        val areaDepotMap = mutableMapOf<Int, DepotItem>()
        transaction {
            itemsFS.sortedBy { it.name }.forEach { item ->
                val depotItem = DepotItem.new {
                    address = item.address
                    latitude = item.latitude
                    longitude = item.longitude
                    name = item.name
                    shareDepot = item.idAreas.containsKey("-1")

                    val openingHoursBuilder = StringBuilder()
                    item.openingHours.toSortedMap().forEach hoursFor@ {entry ->
                        if(entry.value.isNullOrEmpty()) {
                            return@hoursFor
                        }
                        var hoursType = true
                        when(entry.key) {
                            "0MON" -> openingHoursBuilder.append("Lunedì")
                            "1TUE" -> openingHoursBuilder.append("Martedì")
                            "2WED" -> openingHoursBuilder.append("Mercoledì")
                            "3THU" -> openingHoursBuilder.append("Giovedì")
                            "4FRY" -> openingHoursBuilder.append("Venerdì")
                            "5SAT" -> openingHoursBuilder.append("Sabato")
                            "6SUN" -> openingHoursBuilder.append("Domenica")
                            else -> hoursType = false
                        }
                        if(hoursType) {
                            openingHoursBuilder.append(" - ")
                        }
                        openingHoursBuilder.appendln(entry.value)
                    }
                    openingHours = openingHoursBuilder.toString()
                }

                item.idAreas.forEach { (areaId, _) ->
                    areaDepotMap[areaId.toInt()] = depotItem
                }
            }
        }
        return areaDepotMap
    }
}