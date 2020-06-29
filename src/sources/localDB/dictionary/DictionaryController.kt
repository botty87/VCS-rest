package sources.localDB.dictionary

import com.vcs.data.firestoreDB.DictionaryItemFS
import com.vcs.data.localDB.DictionaryItem

interface DictionaryController {
    fun getAll(): List<DictionaryItem>
    fun clear()
    fun putFS(itemsFS: List<DictionaryItemFS>)
}