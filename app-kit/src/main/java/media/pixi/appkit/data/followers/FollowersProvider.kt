package media.pixi.appkit.data.followers

import io.reactivex.Completable
import io.reactivex.Flowable

interface FollowersProvider {

    fun follow(userId: String): Completable

    fun unFollow(userId: String): Completable

    fun getMyFollowers(): Flowable<List<String>>

    fun getFollowers(userId: String): Flowable<List<String>>

}