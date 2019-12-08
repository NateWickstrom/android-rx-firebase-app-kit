package media.pixi.appkit

import media.pixi.appkit.service.messages.FirebaseCloudMessagingService

interface AppKitInjector {

    fun inject(service: FirebaseCloudMessagingService)

    companion object {
        const val INJECTOR = "APP_KIT_INJECTOR"
    }
}
