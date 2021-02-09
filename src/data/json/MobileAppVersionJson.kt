package com.vcs.data.json

import com.vcs.data.base.MobileAppVersionBase
import com.vcs.data.db.MobileAppVersionItem

class MobileAppVersionJson(
    override var minForceVersion: Int,
    override var itunesVersion: String?) : MobileAppVersionBase {

    constructor(mobileAppDataItem: MobileAppVersionItem) : this(
        mobileAppDataItem.minForceVersion,
        mobileAppDataItem.itunesVersion
    )

}

fun MobileAppVersionItem.toJson() = MobileAppVersionJson(this)