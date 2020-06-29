package com.vcs.di

import com.vcs.sources.firestore.*
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
import com.vcs.sources.localDB.retires.RetiresController
import com.vcs.sources.localDB.retires.RetiresControllerImpl
import com.vcs.sources.localDB.trashContainers.TrashContainersController
import com.vcs.sources.localDB.trashContainers.TrashContainersControllerImpl
import com.vcs.tools.DBMigration
import com.vcs.tools.DBMigrationImpl
import org.koin.dsl.module

val firebaseModule = module {
    single { FirestoreRepository() }
    single<DictionaryRepository> { get<FirestoreRepository>() }
    single<DepotsRepository> { get<FirestoreRepository>() }
    single<RetiresRepository> { get<FirestoreRepository>() }
    single<TrashContainersRepository> { get<FirestoreRepository>() }
    single<AreasRepository> { get<FirestoreRepository>() }
}

val controllersModule = module {
    single<DictionaryController> { DictionaryControllerImpl() }
    single<DepotsController> { DepotsControllerImp() }
    single<RetiresController> { RetiresControllerImpl() }
    single<AreasController> { AreasControllerImpl() }
    single<AreasCalendarController> { AreasCalendarControllerImpl() }
    single<TrashContainersController> { TrashContainersControllerImpl() }
    single<AreasTrashContainersController> {AreasTrashContainersControllerImpl() }
}

val toolsModule = module {
    single<DBMigration> { DBMigrationImpl() }
}