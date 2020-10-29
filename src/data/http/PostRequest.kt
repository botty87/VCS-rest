package com.vcs.data.http

import com.vcs.controllers.tokens.TokensController
import com.vcs.data.http.token.TokenCheckResult
import com.vcs.exceptions.TokenExceptions
import com.vcs.exceptions.UserExceptions
import org.koin.core.KoinComponent
import org.koin.core.get

sealed class PostRequest(token: String, checkAdmin: Boolean) : KoinComponent {
    class WithData<T>(token: String, val data: T) : PostRequest(token, false)
    class WithDataAdmin<T>(token: String, val data: T) : PostRequest(token, true)
    class NoDataAdmin(token: String) : PostRequest(token, true)

    init {
        val tokenController: TokensController = get()
        when (val result = tokenController.checkToken(token)) {
            TokenCheckResult.NotActive -> throw TokenExceptions.NotValid()
            is TokenCheckResult.Active -> if(checkAdmin && !result.admin) throw UserExceptions.NotAllowed()
        }
    }
}