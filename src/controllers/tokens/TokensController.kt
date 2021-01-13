package com.vcs.controllers.tokens

import com.vcs.data.db.TokenItem
import com.vcs.data.db.UserItem
import com.vcs.data.http.token.TokenCheckResult

interface TokensController {
    fun create(user: UserItem) : TokenItem
    fun checkToken(token : String) : TokenCheckResult
    fun getUserForToken(token: String) : UserItem
}