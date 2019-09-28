package media.pixi.appkit.domain.notifications

import io.reactivex.Flowable
import javax.inject.Inject

class GetNotifications @Inject constructor() {

    fun getNotifications(): Flowable<List<Notification>> {
        return Flowable.empty()
    }
}