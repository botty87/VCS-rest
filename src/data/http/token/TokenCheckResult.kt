package com.vcs.data.http.token

sealed class TokenCheckResult {
    object NotActive : TokenCheckResult()
    data class Active(val admin: Boolean) : TokenCheckResult()
}