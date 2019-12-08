package media.pixi.appkit.domain.chats

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageBus @Inject constructor() {

    interface MessageListener {
        fun onMessageReceived(chatId: String, message: Message): Boolean
    }

    private val listeners = mutableSetOf<MessageListener>()

    fun addListener(listener: MessageListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: MessageListener) {
        listeners.remove(listener)
    }

    fun sendMessage(chatId: String, message: Message): Boolean {
        var handled = false
        for (listener in listeners) {
            handled = handled || listener.onMessageReceived(chatId, message)
        }
        return handled
    }
}