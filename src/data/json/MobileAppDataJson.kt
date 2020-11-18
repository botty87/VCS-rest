package com.vcs.data.json

import com.vcs.data.base.MobileAppDataBase
import com.vcs.data.db.MobileAppDataItem

class MobileAppDataJson(mobileAppDataItem: MobileAppDataItem) : MobileAppDataBase {
    override var minVersion: Int = mobileAppDataItem.minVersion
    override var minForceVersion: Int = mobileAppDataItem.minForceVersion
}

fun MobileAppDataItem.toJson() = MobileAppDataJson(this)