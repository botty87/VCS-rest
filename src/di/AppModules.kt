package com.vcs.di

import com.vcs.controllers.advices.AdvicesController
import com.vcs.controllers.advices.AdvicesControllerImpl
import com.vcs.controllers.areas2.Areas2Controller
import com.vcs.controllers.areas2.Areas2ControllerImpl
import com.vcs.controllers.mobileAppVersion.MobileAppVersionController
import com.vcs.controllers.mobileAppVersion.MobileAppVersionControllerImpl
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
    single<Areas2Controller> { Areas2ControllerImpl() }
    single<AreasCalendarController> { AreasCalendarControllerImpl() }
    single<TrashContainersController> { TrashContainersControllerImpl() }
    single<AreasTrashContainersController> { AreasTrashContainersControllerImpl() }
    single<TokensController> { TokensControllerImpl() }
    single<UsersController> { UsersControllerImp() }
    single<AdvicesController> { AdvicesControllerImpl() }
    single<MobileAppVersionController> { MobileAppVersionControllerImpl() }
}