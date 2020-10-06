package com.vcs.di

import controllers.areas.AreasController
import controllers.areas.AreasControllerImpl
import controllers.dictionary.DictionaryControllerImpl
import controllers.depots.DepotsController
import controllers.depots.DepotsControllerImp
import controllers.dictionary.DictionaryController
import controllers.referencedTables.areasCalendar.AreasCalendarController
import controllers.referencedTables.areasCalendar.AreasCalendarControllerImpl
import controllers.referencedTables.areasTrashContainers.AreasTrashContainersController
import controllers.referencedTables.areasTrashContainers.AreasTrashContainersControllerImpl
import controllers.retires.RetiresController
import controllers.retires.RetiresControllerImpl
import com.vcs.controllers.trashContainers.TrashContainersController
import controllers.trashContainers.TrashContainersControllerImpl
import org.koin.dsl.module

val controllersModule = module {
    single<DictionaryController> { DictionaryControllerImpl() }
    single<DepotsController> { DepotsControllerImp() }
    single<RetiresController> { RetiresControllerImpl() }
    single<AreasController> { AreasControllerImpl() }
    single<AreasCalendarController> { AreasCalendarControllerImpl() }
    single<TrashContainersController> { TrashContainersControllerImpl() }
    single<AreasTrashContainersController> { AreasTrashContainersControllerImpl() }
}