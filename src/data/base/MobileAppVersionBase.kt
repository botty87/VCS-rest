package com.vcs.data.base

interface MobileAppVersionBase {
    var currentVersion: Int
    var minForceVersion: Int

    companion object {
        const val UNIQUE_ID = 1
    }
}