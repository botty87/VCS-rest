package com.vcs.data.db

import com.vcs.data.dbTables.Retires
import com.vcs.data.dbTables.Tokens
import com.vcs.data.dbTables.Users
import data.db.RetireItem
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TokenItem(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, TokenItem>(Tokens)
    var user by UserItem referencedOn Tokens.user
    var start by Tokens.start
    var active by Tokens.active
}