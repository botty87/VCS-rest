package com.vcs.controllers.users

import com.vcs.data.db.UserItem
import com.vcs.data.http.LoginRequest
import com.vcs.data.json.LoginResponse
import com.vcs.data.json.userItems.EditUserItemJson
import com.vcs.data.json.userItems.UserPassItemJson

interface UsersController {
    fun login(loginRequest: LoginRequest) : LoginResponse
    fun getUsers(token: String) : List<UserItem>
    fun addEdit(userItemJson: EditUserItemJson) : UserItem
    fun changePassword(userPassItemJson: UserPassItemJson)
    fun delete(userItemJson: EditUserItemJson)
}