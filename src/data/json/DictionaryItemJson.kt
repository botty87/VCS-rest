package com.vcs.data.json

import com.vcs.data.base.DictionaryItemBase
import com.vcs.data.localDB.DictionaryItem

class DictionaryItemJson(dictionaryItem: DictionaryItem): DictionaryItemBase {
    val id = dictionaryItem.id.value
    override var name: String = dictionaryItem.name
    override var description: String? = dictionaryItem.description
    override var tagId: Byte = dictionaryItem.tagId
}