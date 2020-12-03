package com.vcs.controllers.mobileAppData

import com.vcs.data.db.MobileAppVersionItem

interface MobileAppVersionController {
    fun get(): MobileAppVersionItem
}