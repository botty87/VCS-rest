package com.vcs.controllers.users

import com.vcs.EnvConstants.IS_RELEASE
import com.vcs.controllers.tokens.TokensController
import com.vcs.data.db.UserItem
import com.vcs.data.dbTables.Users
import com.vcs.data.http.LoginRequest
import com.vcs.data.json.LoginResponse
import com.vcs.data.json.userItems.ChangePasswordItemJson
import com.vcs.data.json.userItems.EditUserItemJson
import com.vcs.data.json.userItems.UserPassItemJson
import com.vcs.exceptions.UserException
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
        } ?: throw UserException.UserNotExist()

        if(!user.active) {
            throw UserException.UserNotActive()
        }

        val password = Crypt.decrypt(user.password)

        if(Crypt.checkHash(loginRequest.password, password)) {
            return LoginResponse(tokensController.create(user).id.value, user.admin)
        }

        throw UserException.WrongPassword()
    }

    override fun setPassword(userPassItemJson: UserPassItemJson) {
        val password = Crypt.encrypt(userPassItemJson.password)
        transaction {
            UserItem[userPassItemJson.id].password = password
        }
    }

    override fun changePassword(token: String, changePasswordItemJson: ChangePasswordItemJson) {
        val user = tokensController.getUserForToken(token)
        val currentPassword = Crypt.decrypt(user.password)
        if(currentPassword != changePasswordItemJson.oldPassword) {
            throw UserException.WrongPassword()
        }
        transaction {
            user.password = Crypt.encrypt(changePasswordItemJson.newPassword)
        }
    }

    override fun getUsers(token: String): List<UserItem> {
        val user = tokensController.getUserForToken(token)
        return transaction {
            UserItem.find {
                Users.id neq user.id.value
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