package data.db

import com.vcs.data.base.DictionaryItemBase
import controllers.dictionary.Dictionary
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DictionaryItem(id: EntityID<Int>) : IntEntity(id), DictionaryItemBase {
    companion object : IntEntityClass<DictionaryItem>(Dictionary)
    override var name: String by Dictionary.name
    override var description: String? by Dictionary.description
    override var tagId: Byte by Dictionary.tagId
}