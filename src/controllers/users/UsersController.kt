package com.vcs.controllers.users

import com.vcs.data.json.LoginResponseJson
import com.vcs.data.json.UserItemJson

interface UsersController {
    fun login(userItemJson: UserItemJson) : LoginResponseJson
}