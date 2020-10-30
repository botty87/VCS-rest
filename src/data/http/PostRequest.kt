package com.vcs.data.http

import com.vcs.controllers.tokens.TokensController
import com.vcs.data.http.token.TokenCheckResult
import com.vcs.data.json.DictionaryItemJson
import com.vcs.exceptions.TokenExceptions
import com.vcs.exceptions.UserExceptions
import org.koin.core.KoinComponent
import org.koin.core.get

sealed class PostRequest(token: String, checkAdmin: Boolean) : KoinComponent {
    class AreaItemJson(token: String, val data: com.vcs.data.json.AreaItemJson) : PostRequest(token, false)
    class DepotItemJson(token: String, val data: com.vcs.data.json.DepotItemJson) : PostRequest(token, false)
    class DictionaryItemJson(token: String, val data: com.vcs.data.json.DictionaryItemJson) : PostRequest(token, false)
    class RetireItemJson(token: String, val data: com.vcs.data.json.RetireItemJson) : PostRequest(token, false)
    class TrashContainerAreasJson(token: String, val data: com.vcs.data.json.TrashContainerAreasJson) : PostRequest(token, false)
    class TrashContainerJson(token: String, val data: com.vcs.data.json.TrashContainerJson) : PostRequest(token, false)

    init {
        val tokenController: TokensController = get()
        if (tokenController.checkToken(token) == TokenCheckResult.NotActive) {
             throw TokenExceptions.NotValid()
        }
    }
}

//is TokenCheckResult.Active -> if (checkAdmin && !result.admin) throw UserExceptions.NotAllowed()