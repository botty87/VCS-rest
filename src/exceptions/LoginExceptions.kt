package com.vcs.exceptions

sealed class LoginExceptions(message: String): Exception(message) {
    class UserNotExist : LoginExceptions("Utente inesistente")
    class UserNotActive : LoginExceptions("Utente non attivo")
    class WrongPassword : LoginExceptions("Password errata")
}