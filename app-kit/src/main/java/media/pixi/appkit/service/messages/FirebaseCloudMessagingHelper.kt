package media.pixi.appkit.service.messages

import com.google.firebase.messaging.RemoteMessage
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.data.chats.ChatProvider
import media.pixi.appkit.data.devices.DevicesProvider
import media.pixi.appkit.domain.chats.MessageBus
import media.pixi.appkit.domain.notifications.GetNotifications
import media.pixi.appkit.domain.notifications.MyNotification
import media.pixi.appkit.domain.notifications.NotificationBus
import media.pixi.appkit.domain.notifications.NotificationHelper
import timber.log.Timber
import javax.inject.Inject

class FirebaseCloudMessagingHelper @Inject constructor(
    private var devicesProvider: DevicesProvider,
    private var authProvider: AuthProvider,
    private var messageBus: MessageBus,
    private var notificationBus: NotificationBus,
    private var notificationHelper: NotificationHelper,
    private var chatProvider: ChatProvider,
    private var getNotifications: GetNotifications
) {

    fun onNewToken(token: String) {
        //todo delete old token when replacing
        Timber.d("New Device Token: $token")
        devicesProvider.deviceId = token

        if (authProvider.isSignedIn()) {
            try {
                devicesProvider.registerDevice().blockingAwait()
            } catch (ex: Exception) {
                Timber.w("Trouble registering device")
            }
        }
    }

    fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Timber.d("notification message received")
        remoteMessage?.let {
            sendNotification(remoteMessage)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val type =
            RemoteMessageType.toEnum(data[FCM_TYPE])

        when (type) {
            RemoteMessageType.FRIEND_REQUEST,
            RemoteMessageType.NEW_FRIEND -> onNotification(data)
            RemoteMessageType.NEW_MESSAGE -> onMessage(data)
        }
    }

    private fun onNotification(data: Map<String, String>) {
        val notificationId = data[NOTIFICATION_ID]

        notificationId?.let {
            getNotifications.getNotification(notificationId)
                .subscribe(
                    this::onNotification,
                    this::onError
                )
        }
    }

    private fun onMessage(data: Map<String, String>) {
        val messageId = data[MESSAGE_ID]
        val chatId = data[CHAT_ID]

        if (messageId != null && chatId != null) {
            chatProvider.getMessage(chatId, messageId)
                .subscribe(
                    { message -> onMessage(chatId, message) },
                    this::onError
                )
        }
    }

    private fun onNotification(notification: MyNotification) {
        if (!notificationBus.sendMessage(notification)) {
            notificationHelper.sendNotification(notification.id)
        }
    }

    private fun onMessage(chatId: String, message: ChatMessageEntity) {
//        if (!messageBus.sendMessage(chatId, message)) {
//            //notificationHelper.sendNotification(notificationId!!)
//        }
    }

    private fun onError(error: Throwable) {
        Timber.e(error)
    }

    companion object {
        private const val FCM_TYPE = "type"

        private const val NOTIFICATION_ID = "notificationId"
        private const val MESSAGE_ID = "messageId"
        private const val CHAT_ID = "chatId"
    }
}