package media.pixi.appkit.data.profile

import io.reactivex.Completable
import io.reactivex.Flowable

interface UserProfileProvider {

    fun observerProfile(): Flowable<UserProfile>

    fun updateProfileImage(url: String): Completable

    fun updateUsername(name: String): Completable

    fun updateFirstname(name: String): Completable

    fun updateLastname(name: String): Completable
}