package media.pixi.appkit.data.notifications

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface NotificationProvider {

    fun getNotifications(): Flowable<List<NotificationEntity>>

    fun getNotification(notificationId: String): Maybe<NotificationEntity>

    fun deleteNotification(notificationId: String): Completable

    fun addNotification(notification: NotificationEntity): Completable

}