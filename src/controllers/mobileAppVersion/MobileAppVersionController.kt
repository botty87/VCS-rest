package com.vcs.controllers.mobileAppVersion

import com.vcs.data.db.MobileAppVersionItem
import com.vcs.data.json.MobileAppVersionJson

interface MobileAppVersionController {
    fun get(iOSVersionNeeded: Boolean): MobileAppVersionItem
    fun set(mobileAppVersionJson: MobileAppVersionJson) : MobileAppVersionItem
}