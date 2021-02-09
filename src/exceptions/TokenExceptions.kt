package com.vcs.exceptions

sealed class TokenExceptions(message: String): Exception(message) {
    class NotValid : TokenExceptions("Invalid token")
    class NotAdmin: TokenExceptions("No admin user")
}