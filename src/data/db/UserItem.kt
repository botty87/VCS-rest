package com.vcs.data.db

import com.vcs.data.base.UserItemBase
import com.vcs.data.dbTables.Users
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserItem(id: EntityID<Int>) : IntEntity(id), UserItemBase {
    companion object : IntEntityClass<UserItem>(Users)
    override var username: String by Users.username
    var password: String by Users.password
    override var active: Boolean by Users.active
    override var admin: Boolean by Users.admin
}