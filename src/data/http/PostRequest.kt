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

    init {
        val tokenController: TokensController = get()
        if (tokenController.checkToken(token) == TokenCheckResult.NotActive) {
             throw TokenExceptions.NotValid()
        }
    }
}