package controllers.retires

import com.vcs.controllers.areas2.Areas2Controller
import com.vcs.data.db.RetireItem2
import com.vcs.data.dbTables.Retires2
import com.vcs.data.json.RetireItem2Json
import data.db.RetireItem
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class RetiresControllerImpl: RetiresController, KoinComponent {

    override fun getAll(): List<RetireItem> {
        return transaction {
            RetireItem.all().toList()
        }
    }

    override fun createNew(retireItemJson: RetireItem2Json): RetireItem2 {
        val areas2Controller: Areas2Controller by inject()

        return transaction {
            val area = areas2Controller.getArea(retireItemJson.areaId!!, false)
            RetireItem2.new {
                freq = retireItemJson.freq
                type = retireItemJson.type
                startDateTime = retireItemJson.startDateTime
                this.area = area
            }
        }
    }

    override fun delete(retireItemId: Int) {
        transaction {
            Retires2.deleteWhere { Retires2.id eq retireItemId }
        }
    }
}