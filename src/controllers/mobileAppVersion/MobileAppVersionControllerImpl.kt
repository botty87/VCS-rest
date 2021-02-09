package com.vcs.controllers.mobileAppVersion

import com.fasterxml.jackson.databind.ObjectMapper
import com.vcs.data.db.MobileAppVersionItem
import com.vcs.data.json.MobileAppVersionJson
import com.vcs.tools.applyIf
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction

class MobileAppVersionControllerImpl: MobileAppVersionController {
    override fun get(iOSVersionNeeded: Boolean): MobileAppVersionItem {
        return transaction {
            MobileAppVersionItem[1]
        }.applyIf(iOSVersionNeeded) {
            val itunesResponse = runBlocking {
                val client = HttpClient()
                val fileResponse: ByteArray = client.request("https://itunes.apple.com/lookup?bundleId=com.vallecamonicaservizi.vcsambiente%20&country=it")
                client.close()
                String(fileResponse)
            }
            val mapper = ObjectMapper()
            val jsonObject = mapper.readTree(itunesResponse)
            itunesVersion = (jsonObject["results"][0]["version"]).textValue()
        }
    }

    override fun set(mobileAppVersionJson: MobileAppVersionJson): MobileAppVersionItem {
        return transaction {
            MobileAppVersionItem[1].apply {
                minForceVersion = mobileAppVersionJson.minForceVersion
            }
        }
    }
}