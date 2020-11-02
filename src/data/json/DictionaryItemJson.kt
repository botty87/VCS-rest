package com.vcs.data.json

import com.vcs.data.base.DictionaryItemBase
import data.db.DictionaryItem

class DictionaryItemJson(
        val id: Int,
        override var name: String,
        override var description: String?,
        override var tagId: Byte) : DictionaryItemBase {

    constructor(dictionaryItem: DictionaryItem) : this(
            dictionaryItem.id.value,
            dictionaryItem.name,
            dictionaryItem.description,
            dictionaryItem.tagId
    )
}