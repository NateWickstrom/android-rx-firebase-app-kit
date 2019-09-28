package media.pixi.appkit.data.notifications

import io.reactivex.Flowable

interface NotificationProvider {

    fun getNotifications(): Flowable<List<String>>

}