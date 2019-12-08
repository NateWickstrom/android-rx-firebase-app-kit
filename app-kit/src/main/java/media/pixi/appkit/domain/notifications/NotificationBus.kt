package media.pixi.appkit.domain.notifications

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationBus @Inject constructor() {

    interface NotificationListener {
        fun onMessageReceived(message: MyNotification): Boolean
    }

    private val listeners = mutableSetOf<NotificationListener>()

    fun addListener(listener: NotificationListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: NotificationListener) {
        listeners.remove(listener)
    }

    fun sendMessage(message: MyNotification): Boolean {
        var handled = false
        for (listener in listeners) {
            handled = handled || listener.onMessageReceived(message)
        }
        return handled
    }
}