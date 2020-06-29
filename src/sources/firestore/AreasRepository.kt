package com.vcs.sources.firestore

import com.vcs.data.firestoreDB.AreaItemFS

interface AreasRepository {
    fun getAreas(): List<AreaItemFS>
}