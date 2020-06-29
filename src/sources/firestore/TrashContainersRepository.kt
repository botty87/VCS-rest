package com.vcs.sources.firestore

import com.google.common.collect.Multimap
import com.vcs.data.firestoreDB.TrashContainerFS

interface TrashContainersRepository {
    fun getTrashContainers(): Multimap<String, TrashContainerFS>
}