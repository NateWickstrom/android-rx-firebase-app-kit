package media.pixi.appkit.domain.notifications

import io.reactivex.Completable
import media.pixi.appkit.data.notifications.NotificationProvider
import javax.inject.Inject

class AddNotifications @Inject constructor(private val notificationProvider: NotificationProvider) {

    fun addNotification(notification: MyNotification): Completable {
        return notificationProvider.addNotification(notification.entity)
    }
}