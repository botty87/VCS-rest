package com.vcs.data.http

sealed class PostResult<out T> {
    data class Error(val error: String) : PostResult<Nothing>()
    data class Success<T>(val value: T? = null) : PostResult<T>()
}