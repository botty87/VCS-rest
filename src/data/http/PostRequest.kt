package com.vcs.data.http

import com.vcs.controllers.tokens.TokensController
import com.vcs.data.http.token.TokenCheckResult
import com.vcs.exceptions.TokenExceptions
import org.koin.core.KoinComponent
import org.koin.core.get

sealed class PostRequest(token: String) : KoinComponent {
    class AreaItemJson(token: String, val data: com.vcs.data.json.AreaItemJson) : PostRequest(token)
    class DepotItemJson(token: String, val data: com.vcs.data.json.DepotItemJson) : PostRequest(token)
    class DictionaryItemJson(token: String, val data: com.vcs.data.json.DictionaryItemJson) : PostRequest(token)
    class RetireItemJson(token: String, val data: com.vcs.data.json.RetireItemJson) : PostRequest(token)
    class TrashContainerAreasJson(token: String, val data: com.vcs.data.json.TrashContainerAreasJson) : PostRequest(token)
    class TrashContainerJson(token: String, val data: com.vcs.data.json.TrashContainerJson) : PostRequest(token)
    class AdviceItemJson(token: String, val data: com.vcs.data.json.AdviceItemJson) : PostRequest(token)
    class NoDataAdmin(token: String) : PostRequest(token) {
        override fun checkToken(token: String): TokenCheckResult.Active {
            val tokenResult = super.checkToken(token)
            if(!tokenResult.admin) {
                throw TokenExceptions.NotAdmin();
            }
            return tokenResult;
        }
    }

    init {
        checkToken(token)
    }

    open fun checkToken(token: String): TokenCheckResult.Active {
        val tokenController: TokensController = get()
        val tokenResult = tokenController.checkToken(token)
        if (tokenResult == TokenCheckResult.NotActive) {
            throw TokenExceptions.NotValid()
        }
        return tokenResult as TokenCheckResult.Active
    }
}