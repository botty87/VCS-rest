package com.vcs.tools

interface DBMigration {
    fun fromFirestoreToLocal(): String
}