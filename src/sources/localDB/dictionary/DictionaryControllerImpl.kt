package com.vcs.sources.localDB.dictionary

import com.vcs.data.firestoreDB.DictionaryItemFS
import com.vcs.data.json.DictionaryItemJson
import com.vcs.data.localDB.DictionaryItem
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class DictionaryControllerImpl: DictionaryController {

    override fun getAll(): List<DictionaryItem> {
        return transaction {
            DictionaryItem.all().toList()
        }
    }

    override fun clear() {
        transaction {
            Dictionary.deleteAll()
        }
    }

    override fun putFS(itemsFS: List<DictionaryItemFS>) {
        transaction {
            itemsFS.forEach { item ->
                DictionaryItem.new {
                    name = item.name
                    description = item.description
                    tagId = item.tagID.toByte()
                }
            }
        }
    }

    override fun createNew(dictionaryItemJson: DictionaryItemJson): DictionaryItem {
        return transaction {
            DictionaryItem.new {
                name = dictionaryItemJson.name
                description = dictionaryItemJson.description
                tagId = dictionaryItemJson.tagId
            }
        }
    }

    override fun update(dictionaryItemJson: DictionaryItemJson): DictionaryItem {
        return transaction {
            DictionaryItem[dictionaryItemJson.id].apply {
                name = dictionaryItemJson.name
                description = dictionaryItemJson.description
                tagId = dictionaryItemJson.tagId
            }
        }
    }

    override fun delete(dictionaryItemJson: DictionaryItemJson) {
        transaction {
            DictionaryItem[dictionaryItemJson.id].delete()
        }
    }
}