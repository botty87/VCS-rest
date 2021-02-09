package com.vcs.data.dbTables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.datetime

object Tokens: IdTable<String>() {
    override val id: Column<EntityID<String>> = varchar("token", 32).entityId()
    override val primaryKey: PrimaryKey = PrimaryKey(id)

    val user = reference("user", Users)
    val start = datetime("start")
    val active = bool("active").default(true)
}