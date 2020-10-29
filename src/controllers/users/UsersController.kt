package com.vcs.controllers.users

import com.vcs.data.db.UserItem
import com.vcs.data.http.LoginRequest
import com.vcs.data.json.LoginResponse

interface UsersController {
    fun login(loginRequest: LoginRequest) : LoginResponse
    fun getUsers() : List<UserItem>
}