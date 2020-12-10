package com.vcs.controllers.mobileAppVersion

import com.vcs.data.db.MobileAppVersionItem

interface MobileAppVersionController {
    fun get(iOSVersionNeeded: Boolean): MobileAppVersionItem
}