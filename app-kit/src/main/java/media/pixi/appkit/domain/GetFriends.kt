package media.pixi.appkit.domain

import io.reactivex.Flowable
import media.pixi.appkit.data.friends.FriendsProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import javax.inject.Inject

class GetFriends @Inject constructor(private val userProfileProvider: UserProfileProvider,
                                     private val friendsProvider: FriendsProvider) {

    fun getFriendsForUser(userId: String) : Flowable<Iterator<UserProfile>> {
        return friendsProvider.getFriendsForUser(userId)
            .concatMap { toProfiles(it.ids) }
    }

    private fun toProfiles(userIds: Iterable<String>) : Flowable<Iterator<UserProfile>> {
        val flowables = userIds.map { userProfileProvider.observerUserProfile(it) }

        return Flowable.zipIterable(flowables, { list -> list.map { it as UserProfile }.iterator()}, true, 1)
    }
}