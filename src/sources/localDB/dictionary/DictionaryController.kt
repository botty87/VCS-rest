package com.vcs.sources.localDB.dictionary

import com.vcs.data.firestoreDB.DictionaryItemFS
import com.vcs.data.json.DictionaryItemJson
import com.vcs.data.localDB.DictionaryItem

interface DictionaryController {
    fun getAll(): List<DictionaryItem>
    fun clear()
    fun putFS(itemsFS: List<DictionaryItemFS>)
    fun createNew(dictionaryItemJson: DictionaryItemJson): DictionaryItem
    fun update(dictionaryItemJson: DictionaryItemJson): DictionaryItem
    fun delete(dictionaryItemJson: DictionaryItemJson)
}