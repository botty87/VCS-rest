package com.vcs.sources.localDB.dictionary

import org.jetbrains.exposed.dao.id.IntIdTable

object Dictionary: IntIdTable() {
    val name = text("name").uniqueIndex()
    val description = text("description").nullable()
    val tagId = byte("tagId")
}