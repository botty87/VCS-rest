package com.vcs.tools

import com.vcs.sources.firestore.*
import com.vcs.sources.localDB.areas.AreasController
import com.vcs.sources.localDB.depots.DepotsController
import com.vcs.sources.localDB.dictionary.DictionaryController
import com.vcs.sources.localDB.referencedTables.areasCalendar.AreasCalendarController
import com.vcs.sources.localDB.referencedTables.areasTrashContainers.AreasTrashContainersController
import com.vcs.sources.localDB.retires.RetiresController
import com.vcs.sources.localDB.trashContainers.TrashContainersController
import org.koin.core.KoinComponent
import org.koin.core.inject

class DBMigrationImpl: DBMigration, KoinComponent {
    private val dictionaryRepository: DictionaryRepository by inject()
    private val depotsRepository: DepotsRepository by inject()
    private val retiresRepository: RetiresRepository by inject()
    private val trashContsRepository: TrashContainersRepository by inject()
    private val areasRepository: AreasRepository by inject()

    private val dictionaryController: DictionaryController by inject()
    private val depotsController: DepotsController by inject()
    private val retiresController: RetiresController by inject()
    private val areasController: AreasController by inject()
    private val areasCalendarController: AreasCalendarController by inject()
    private val trashContainersController: TrashContainersController by inject()
    private val areasTrashContainersController: AreasTrashContainersController by inject()

    override fun fromFirestoreToLocal(): String {

        try {
            val dictionary = dictionaryRepository.getDictionary()
            val depots = depotsRepository.getDepots()
            val retires = retiresRepository.getRetires()
            val trashContainers = trashContsRepository.getTrashContainers()
            val areas = areasRepository.getAreas()

            clearLocalDB()

            dictionaryController.putFS(dictionary)
            retiresController.putFS(retires)

            val trashContainersTownMap = trashContainersController.putFS(trashContainers)
            val areaDepotMap = depotsController.putFS(depots)
            areasController.putFS(areas, areaDepotMap, trashContainersTownMap)
        } catch (e: Exception) {
            return e.localizedMessage
        }

        return "Sync completed!"
    }

    private fun clearLocalDB() {
        areasTrashContainersController.clear()
        areasCalendarController.clear()
        trashContainersController.clear()
        areasController.clear()
        depotsController.clear()
        dictionaryController.clear()
        retiresController.clear()
    }
}