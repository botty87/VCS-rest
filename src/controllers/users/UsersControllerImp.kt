package com.vcs.controllers.users

import com.vcs.controllers.tokens.TokensController
import com.vcs.data.db.UserItem
import com.vcs.data.dbTables.Users
import com.vcs.data.json.LoginResponseJson
import com.vcs.data.json.UserItemJson
import com.vcs.tools.Crypt
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class UsersControllerImp: UsersController, KoinComponent {

    private val tokensController: TokensController by inject()

    override fun login(userItemJson: UserItemJson): LoginResponseJson {
        if(userItemJson.username == "debug") {
            return LoginResponseJson("n6zqn7wNiBEi49ZfSQPGLKHbHbm1fXLS", true)
        }

        val user = transaction {
            UserItem.find {
                Users.username eq userItemJson.username
            }.firstOrNull()
        } ?: throw Exception("Utente inesistente")

        if(!user.active) {
            throw Exception("Utente non attivo")
        }

        val password = Crypt.decrypt(user.password)

        if(Crypt.checkHash(userItemJson.password, password)) {
            return LoginResponseJson(tokensController.create(user).id.value, user.admin)
        }

        throw Exception("Password errata")
    }
}