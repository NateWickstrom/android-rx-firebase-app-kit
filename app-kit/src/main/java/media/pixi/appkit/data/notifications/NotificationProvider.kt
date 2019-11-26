package media.pixi.appkit.data.notifications

import io.reactivex.Completable
import io.reactivex.Flowable

interface NotificationProvider {

    fun getNotifications(): Flowable<List<NotificationEntity>>

    fun deleteNotification(notificationId: String): Completable

    fun addNotification(notification: NotificationEntity): Completable

}