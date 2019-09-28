package media.pixi.appkit.domain.notifications

import io.reactivex.Flowable
import media.pixi.appkit.data.notifications.NotificationProvider
import javax.inject.Inject

class GetNotifications @Inject constructor(private val notificationProvider: NotificationProvider) {

    fun getNotifications(): Flowable<List<Notification>> {
        return notificationProvider.getNotifications()
            .map { list -> list.map { MyNotification(id = it) } }
    }

    data class MyNotification(override val id: String): Notification
}