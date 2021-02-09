package com.vcs.data.json.userItems

class EditUserItemJson(id: Int, username: String, active: Boolean, admin: Boolean, val password: String?) :
    UserItemJson(id, username, active, admin)