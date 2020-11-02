package com.vcs.data.db


import com.vcs.data.dbTables.Tokens
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class TokenItem(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, TokenItem>(Tokens)
    var user by UserItem referencedOn Tokens.user
    var start by Tokens.start
    var active by Tokens.active
}

val TokenItem.notActive : Boolean
    get() = !this.active

fun TokenItem.isOld() : Boolean {
    val hours = ChronoUnit.HOURS.between(this.start, LocalDateTime.now())
    return hours >= 1
}