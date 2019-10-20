package media.pixi.appkit.data.friends

import io.reactivex.Flowable

interface FriendsProvider {

    fun getFriendsForUser(userId: String): Flowable<List<String>>
    fun getFriends(): Flowable<List<String>>

}