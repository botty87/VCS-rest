package com.vcs.data.base

interface MobileAppVersionBase {
    var minForceVersion: Int
    var itunesVersion: String?

    companion object {
        const val UNIQUE_ID = 1
    }
}