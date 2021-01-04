package com.vcs.data.json

import com.vcs.data.base.UserItemBase
import com.vcs.data.db.UserItem

open class UserItemJson(
    val id: Int,
    override var username: String,
    override var active: Boolean,
    override var admin: Boolean,
) : UserItemBase{
    constructor(userItem: UserItem) : this(
        userItem.id.value,
        userItem.username,
        userItem.active,
        userItem.admin
    )
}

class NewUserItemJson(id: Int, username: String, active: Boolean, admin: Boolean, val pwdHash: String) :
    UserItemJson(id, username, active, admin)

fun UserItem.toJson() = UserItemJson(this)