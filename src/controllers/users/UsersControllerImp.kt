package com.vcs.controllers.users

import com.vcs.EnvConstants.IS_RELEASE
import com.vcs.controllers.tokens.TokensController
import com.vcs.data.db.UserItem
import com.vcs.data.dbTables.Users
import com.vcs.data.http.LoginRequest
import com.vcs.data.json.LoginResponse
import com.vcs.data.json.userItems.EditUserItemJson
import com.vcs.data.json.userItems.UserPassItemJson
import com.vcs.exceptions.LoginExceptions
import com.vcs.tools.Crypt
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class UsersControllerImp: UsersController, KoinComponent {

    private val tokensController: TokensController by inject()

    override fun login(loginRequest: LoginRequest): LoginResponse {
        if(loginRequest.username == "debug" && !IS_RELEASE) {
            return LoginResponse("n6zqn7wNiBEi49ZfSQPGLKHbHbm1fXLS", true)
        }

        val user = transaction {
            UserItem.find {
                Users.username eq loginRequest.username
            }.firstOrNull()
        } ?: throw LoginExceptions.UserNotExist()

        if(!user.active) {
            throw LoginExceptions.UserNotActive()
        }

        val password = Crypt.decrypt(user.password)

        if(Crypt.checkHash(loginRequest.password, password)) {
            return LoginResponse(tokensController.create(user).id.value, user.admin)
        }

        throw LoginExceptions.WrongPassword()
    }

    override fun changePassword(userPassItemJson: UserPassItemJson) {
        val password = Crypt.encrypt(userPassItemJson.password)
        transaction {
            UserItem[userPassItemJson.id].password = password
        }
    }

    override fun getUsers(token: String): List<UserItem> {
        val userId = tokensController.getUserIdForToken(token)
        return transaction {
            UserItem.find {
                Users.id neq userId
            }.orderBy(Users.username to SortOrder.ASC).toList()
        }
    }

    override fun addEdit(userItemJson: EditUserItemJson): UserItem {
        return transaction {
            if(userItemJson.id == 0) {
                UserItem.new {
                    username = userItemJson.username
                    active = userItemJson.active
                    admin = userItemJson.admin
                    password = Crypt.encrypt(userItemJson.password!!)
                }
            } else {
                UserItem[userItemJson.id].apply {
                    username = userItemJson.username
                    active = userItemJson.active
                    admin = userItemJson.admin
                }
            }
        }
    }

    override fun delete(userItemJson: EditUserItemJson) {
        transaction {
            UserItem[userItemJson.id].delete()
        }
    }
}