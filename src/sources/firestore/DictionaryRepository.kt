package com.vcs.sources.firestore

import com.vcs.data.firestoreDB.DictionaryItemFS

interface DictionaryRepository {
    fun getDictionary() : List<DictionaryItemFS>
}