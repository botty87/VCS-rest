package com.vcs.di

import com.vcs.controllers.advices.AdvicesController
import com.vcs.controllers.advices.AdvicesControllerImpl
import com.vcs.controllers.mobileAppData.MobileAppDataController
import com.vcs.controllers.mobileAppData.MobileAppDataControllerImpl
import com.vcs.controllers.tokens.TokensController
import com.vcs.controllers.tokens.TokensControllerImpl
import com.vcs.controllers.trashContainers.TrashContainersController
import com.vcs.controllers.users.UsersController
import com.vcs.controllers.users.UsersControllerImp
import controllers.areas.AreasController
import controllers.areas.AreasControllerImpl
import controllers.depots.DepotsController
import controllers.depots.DepotsControllerImp
import controllers.dictionary.DictionaryController
import controllers.dictionary.DictionaryControllerImpl
import controllers.referencedTables.areasCalendar.AreasCalendarController
import controllers.referencedTables.areasCalendar.AreasCalendarControllerImpl
import controllers.referencedTables.areasTrashContainers.AreasTrashContainersController
import controllers.referencedTables.areasTrashContainers.AreasTrashContainersControllerImpl
import controllers.retires.RetiresController
import controllers.retires.RetiresControllerImpl
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
    single<TokensController> { TokensControllerImpl() }
    single<UsersController> { UsersControllerImp() }
    single<AdvicesController> { AdvicesControllerImpl() }
    single<MobileAppDataController> { MobileAppDataControllerImpl() }
}