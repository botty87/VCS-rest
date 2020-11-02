package com.vcs.exceptions

sealed class UserExceptions(message: String) : Exception(message) {
    class NotAllowed : UserExceptions("Utente non autorizzato")
}