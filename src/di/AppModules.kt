package com.vcs.di

import com.vcs.sources.localDB.areas.AreasController
import com.vcs.sources.localDB.areas.AreasControllerImpl
import com.vcs.sources.localDB.dictionary.DictionaryControllerImpl
import com.vcs.sources.localDB.depots.DepotsController
import com.vcs.sources.localDB.depots.DepotsControllerImp
import com.vcs.sources.localDB.dictionary.DictionaryController
import com.vcs.sources.localDB.referencedTables.areasCalendar.AreasCalendarController
import com.vcs.sources.localDB.referencedTables.areasCalendar.AreasCalendarControllerImpl
import com.vcs.sources.localDB.referencedTables.areasTrashContainers.AreasTrashContainersController
import com.vcs.sources.localDB.referencedTables.areasTrashContainers.AreasTrashContainersControllerImpl
import com.vcs.sources.localDB.referencedTables.areasTrashContainersNew.AreasTrashContainersControllerImplNew
import com.vcs.sources.localDB.referencedTables.areasTrashContainersNew.AreasTrashContainersControllerNew
import com.vcs.sources.localDB.retires.RetiresController
import com.vcs.sources.localDB.retires.RetiresControllerImpl
import com.vcs.sources.localDB.trashContainers.TrashContainersController
import com.vcs.sources.localDB.trashContainers.TrashContainersControllerImpl
import org.koin.dsl.module

val controllersModule = module {
    single<DictionaryController> { DictionaryControllerImpl() }
    single<DepotsController> { DepotsControllerImp() }
    single<RetiresController> { RetiresControllerImpl() }
    single<AreasController> { AreasControllerImpl() }
    single<AreasCalendarController> { AreasCalendarControllerImpl() }
    single<TrashContainersController> { TrashContainersControllerImpl() }
    single<AreasTrashContainersController> {AreasTrashContainersControllerImpl() }
    single<AreasTrashContainersControllerNew> {AreasTrashContainersControllerImplNew() }
}