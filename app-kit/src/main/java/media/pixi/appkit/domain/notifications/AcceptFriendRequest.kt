package media.pixi.appkit.domain.notifications

import io.reactivex.Completable
import javax.inject.Inject

class AcceptFriendRequest @Inject constructor() {

    fun accept(friendRequestNotification: FriendRequestNotification): Completable {
        return Completable.complete()
    }
}