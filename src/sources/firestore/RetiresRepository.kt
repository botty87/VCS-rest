package com.vcs.sources.firestore

import com.vcs.data.firestoreDB.RetireItemFS

interface RetiresRepository {
    fun getRetires(): List<RetireItemFS>
}