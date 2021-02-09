package com.vcs.data.dbTables

import org.jetbrains.exposed.dao.id.IntIdTable

object Depots: IntIdTable() {
    val address = text("address")
    val latitude = double("latitude")
    val longitude = double("longitude")
    val name = text("name")
    val openingHours = text("open_hours")
    val shareDepot = bool("share")
}