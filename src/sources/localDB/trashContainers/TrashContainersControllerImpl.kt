package com.vcs.sources.localDB.trashContainers

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import com.vcs.data.firestoreDB.TrashContainerFS
import com.vcs.data.localDB.TrashContainerItem
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class TrashContainersControllerImpl: TrashContainersController {
    override fun getAll(): List<TrashContainerItem> {
        return transaction {
            TrashContainerItem.all().toList()
        }
    }

    override fun clear() {
        transaction {
            TrashContainers.deleteAll()
        }
    }

    override fun putFS(trashContainersFS: Multimap<String, TrashContainerFS>): Multimap<String, TrashContainerItem> {
        val trashContainers: Multimap<String, TrashContainerItem> = ArrayListMultimap.create()
        transaction {
            trashContainersFS.keySet().forEach { town ->
                trashContainersFS[town].forEach { trashContainerFS ->
                    TrashContainerItem.new {
                        address = trashContainerFS.address
                        latitude = trashContainerFS.latitude
                        longitude = trashContainerFS.longitude
                    }.run { trashContainers.put(town, this) }
                }
            }
        }
        return trashContainers
    }
}