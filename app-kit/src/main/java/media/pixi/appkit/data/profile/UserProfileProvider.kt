package media.pixi.appkit.data.profile

import io.reactivex.Completable
import io.reactivex.Flowable

interface UserProfileProvider {

    fun observerUserProfile(userId: String): Flowable<UserProfile>

    fun addFriend(userId: String): Completable

    fun unFriend(userId: String): Completable

    fun block(userId: String): Completable

    // observer friends for user

}