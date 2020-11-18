package com.vcs.controllers.mobileAppData

import com.vcs.data.db.MobileAppDataItem

interface MobileAppDataController {
    fun get(): MobileAppDataItem
}