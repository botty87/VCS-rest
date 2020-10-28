package com.vcs.controllers.tokens

import com.vcs.data.db.TokenItem
import com.vcs.data.db.UserItem

interface TokensController {
    fun create(user: UserItem) : TokenItem
    fun checkToken(token : String) : Boolean
}