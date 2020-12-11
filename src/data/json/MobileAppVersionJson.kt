package com.vcs.data.json

import com.vcs.data.base.MobileAppVersionBase
import com.vcs.data.db.MobileAppVersionItem

class MobileAppVersionJson(mobileAppDataItem: MobileAppVersionItem) : MobileAppVersionBase {
    override var minForceVersion: Int = mobileAppDataItem.minForceVersion
    override var itunesVersion = mobileAppDataItem.itunesVersion
}

fun MobileAppVersionItem.toJson() = MobileAppVersionJson(this)