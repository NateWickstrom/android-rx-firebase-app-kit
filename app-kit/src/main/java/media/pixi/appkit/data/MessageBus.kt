package media.pixi.appkit.data

import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageBus @Inject constructor() {

    interface MessageListener {
        fun onMessageReceived(message: RemoteMessage): Boolean
    }

    private val listeners = mutableSetOf<MessageListener>()

    fun addListener(listener: MessageListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: MessageListener) {
        listeners.remove(listener)
    }

    fun sendMessage(message: RemoteMessage): Boolean {
        var handled = false
        for (listener in listeners) {
            handled = handled || listener.onMessageReceived(message)
        }
        return handled
    }
}