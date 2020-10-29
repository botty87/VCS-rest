package com.vcs.data.json

import com.vcs.data.base.UserItemBase
import com.vcs.data.db.UserItem

class UserItemJson(userItem: UserItem) : UserItemBase {
    override var username: String = userItem.username
    override var active: Boolean = userItem.active
    override var admin: Boolean = userItem.admin
}