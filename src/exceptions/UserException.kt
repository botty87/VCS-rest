package com.vcs.exceptions

sealed class UserException(message: String): Exception(message) {
    class UserNotExist : UserException("Utente inesistente")
    class UserNotActive : UserException("Utente non attivo")
    class WrongPassword : UserException("Password errata")
    class NotAuthorized : UserException("Utente non autorizzato!")
}