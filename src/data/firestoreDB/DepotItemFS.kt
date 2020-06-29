package com.vcs.data.firestoreDB

data class DepotItemFS(
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val name: String = "",
    val idAreas: Map<String, Boolean> = emptyMap(),
    val openingHours: Map<String, String?> = emptyMap()
)