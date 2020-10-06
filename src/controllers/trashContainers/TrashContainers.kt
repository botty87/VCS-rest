package com.vcs.controllers.trashContainers

import org.jetbrains.exposed.dao.id.IntIdTable

object TrashContainers: IntIdTable() {
    val address = TrashContainers.text("address")
    val latitude = TrashContainers.double("latitude")
    val longitude = TrashContainers.double("longitude")
}