package media.pixi.appkit.domain.users

import io.reactivex.Single
import media.pixi.appkit.data.followers.FollowersProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import javax.inject.Inject

class GetFollowersTask @Inject constructor(private val userProfileProvider: UserProfileProvider,
                                           private val followersProvider: FollowersProvider) {

    fun getFollowersForUser(userId: String) : Single<List<UserProfile>> {
        return followersProvider.getFollowers(userId)
            .firstOrError()
            .flatMap { toProfiles(it) }
    }

    fun getMyFollowers() : Single<List<UserProfile>> {
        return followersProvider.getMyFollowers()
            .firstOrError()
            .flatMap { toProfiles(it) }
    }

    private fun toProfiles(userIds: List<String>) : Single<List<UserProfile>> {
        val users = userIds.map { userProfileProvider.observerUserProfile(it).firstOrError() }
        return Single.zipArray({ list -> list.map { it as UserProfile }}, users.toTypedArray())
    }
}