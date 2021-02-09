package controllers.dictionary

import com.vcs.data.dbTables.Dictionary
import com.vcs.data.json.DictionaryItemJson
import data.db.DictionaryItem
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class DictionaryControllerImpl: DictionaryController {

    override fun getAll(): List<DictionaryItem> {
        return transaction {
            DictionaryItem.all().sortedBy { it.name.toLowerCase() }
        }
    }

    override fun clear() {
        transaction {
            Dictionary.deleteAll()
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