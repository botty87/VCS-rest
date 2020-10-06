package controllers.dictionary

import com.vcs.data.json.DictionaryItemJson
import data.db.DictionaryItem

interface DictionaryController {
    fun getAll(): List<DictionaryItem>
    fun clear()
    fun createNew(dictionaryItemJson: DictionaryItemJson): DictionaryItem
    fun update(dictionaryItemJson: DictionaryItemJson): DictionaryItem
    fun delete(dictionaryItemJson: DictionaryItemJson)
}