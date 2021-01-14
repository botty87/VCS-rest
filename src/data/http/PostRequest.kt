@file:Suppress("LeakingThis")

package com.vcs.data.http

import com.fasterxml.jackson.annotation.JsonProperty
import com.vcs.controllers.tokens.TokensController
import com.vcs.data.http.token.TokenCheckResult
import com.vcs.data.json.userItems.ChangePasswordItemJson
import com.vcs.data.json.userItems.UserPassItemJson
import com.vcs.exceptions.TokenExceptions
import org.koin.core.KoinComponent
import org.koin.core.get

sealed class PostRequest(val token: String) : KoinComponent {
    class AreaItemJson(@JsonProperty("token") token: String, val data: com.vcs.data.json.AreaItemJson) : PostRequest(token)
    class DepotItemJson(@JsonProperty("token") token: String, val data: com.vcs.data.json.DepotItemJson) : PostRequest(token)
    class DictionaryItemJson(@JsonProperty("token") token: String, val data: com.vcs.data.json.DictionaryItemJson) : PostRequest(token)
    class RetireItemJson(@JsonProperty("token") token: String, val data: com.vcs.data.json.RetireItemJson) : PostRequest(token)
    class TrashContainerAreasJson(@JsonProperty("token") token: String, val data: com.vcs.data.json.TrashContainerAreasJson) : PostRequest(token)
    class TrashContainerJson(@JsonProperty("token") token: String, val data: com.vcs.data.json.TrashContainerJson) : PostRequest(token)
    class AdviceItemJson(@JsonProperty("token") token: String, val data: com.vcs.data.json.AdviceItemJson) : PostRequest(token)
    class ChangePassword(@JsonProperty("token") token: String, val data: ChangePasswordItemJson) : PostRequest(token)

    //ADMIN REQUESTS
    open class NoDataAdmin(@JsonProperty("token") token: String) : PostRequest(token) {
        override fun checkToken(token: String): TokenCheckResult.Active {
            val tokenResult = super.checkToken(token)
            if(!tokenResult.admin) {
                throw TokenExceptions.NotAdmin()
            }
            return tokenResult
        }
    }
    class SetPassword(@JsonProperty("token") token: String, val data: UserPassItemJson) : NoDataAdmin(token)
    class UserItemJson(@JsonProperty("token") token: String, val data: com.vcs.data.json.userItems.EditUserItemJson) : NoDataAdmin(token)
    class MobileAppVersionJson(@JsonProperty("token") token: String, val data: com.vcs.data.json.MobileAppVersionJson) : NoDataAdmin(token)

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