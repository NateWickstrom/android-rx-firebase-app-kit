package media.pixi.appkit.data.friends

import io.reactivex.Flowable

interface FriendsProvider {

    fun getFriendsForUser(userId: String): Flowable<FriendsResult>
}