package com.vcs.data.http

import com.vcs.controllers.tokens.TokensController
import org.koin.core.KoinComponent
import org.koin.core.get

class PostRequest<T>(
        token : String,
        val data : T
) : KoinComponent {
    init {
        val tokenController: TokensController = get()
        if(!tokenController.checkToken(token)) {
            throw InvalidTokenException()
        }
    }
}