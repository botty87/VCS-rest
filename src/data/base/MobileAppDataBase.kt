package com.vcs.data.base

interface MobileAppDataBase {
    var minVersion: Int
    var minForceVersion: Int

    companion object {
        const val UNIQUE_ID = 1
    }
}