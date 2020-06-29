package com.vcs.sources.firestore

import com.vcs.data.firestoreDB.DepotItemFS

interface DepotsRepository {
    fun getDepots(): List<DepotItemFS>
}