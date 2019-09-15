package media.pixi.appkit

import media.pixi.appkit.service.AppKitNotificationService

interface AppKitInjector {

    fun inject(service: AppKitNotificationService)

    companion object {
        const val INJECTOR = "APP_KIT_INJECTOR"
    }
}
