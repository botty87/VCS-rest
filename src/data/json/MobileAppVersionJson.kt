package com.vcs.data.json

import com.vcs.data.base.MobileAppVersionBase
import com.vcs.data.db.MobileAppVersionItem

class MobileAppVersionJson(mobileAppDataItem: MobileAppVersionItem) : MobileAppVersionBase {
    override var currentVersion: Int = mobileAppDataItem.currentVersion
    override var minForceVersion: Int = mobileAppDataItem.minForceVersion
}

fun MobileAppVersionItem.toJson() = MobileAppVersionJson(this)