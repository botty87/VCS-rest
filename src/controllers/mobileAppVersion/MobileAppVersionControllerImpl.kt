package com.vcs.controllers.mobileAppData

import com.vcs.data.db.MobileAppVersionItem
import org.jetbrains.exposed.sql.transactions.transaction

class MobileAppVersionControllerImpl: MobileAppVersionController {
    override fun get(): MobileAppVersionItem {
        return transaction {
            MobileAppVersionItem[1]
        }
    }
}