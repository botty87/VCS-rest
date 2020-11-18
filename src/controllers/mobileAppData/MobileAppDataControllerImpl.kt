package com.vcs.controllers.mobileAppData

import com.vcs.data.db.MobileAppDataItem
import org.jetbrains.exposed.sql.transactions.transaction

class MobileAppDataControllerImpl: MobileAppDataController {
    override fun get(): MobileAppDataItem {
        return transaction {
            MobileAppDataItem[1]
        }
    }
}