package com.vcs.data.json

import com.vcs.data.base.UserItemBase
import com.vcs.data.db.UserItem

class UserItemJson : UserItemBase {
    override var username: String
    override var password: String

    constructor(userItem: UserItem) {
        this.username = userItem.username
        this.password = userItem.password
    }

    //Used by jackson
    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}