package com.vcs.data.json.userItems

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

fun UserItem.toJson() = UserItemJson(this)