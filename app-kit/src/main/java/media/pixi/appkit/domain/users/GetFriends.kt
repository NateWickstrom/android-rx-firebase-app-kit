package media.pixi.appkit.domain.users

import io.reactivex.Flowable
import media.pixi.appkit.data.friends.FriendsProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import javax.inject.Inject

class GetFriends @Inject constructor(private val userProfileProvider: UserProfileProvider,
                                     private val friendsProvider: FriendsProvider) {

    fun getFriends() : Flowable<List<UserProfile>> {
        return friendsProvider.getFriends()
            .concatMap { toProfiles(it) }
            .map { sort(it) }
    }

    fun getFriendsForUser(userId: String) : Flowable<List<UserProfile>> {
        return friendsProvider.getFriendsForUser(userId)
            .concatMap { toProfiles(it) }
            .map { sort(it) }
    }

    private fun toProfiles(userIds: Iterable<String>) : Flowable<List<UserProfile>> {
        val flowables = userIds.map { userProfileProvider.observerUserProfile(it) }

        return Flowable.zipIterable(flowables, { list ->
            list.map { it as UserProfile } }, true, 1)
    }

    private fun sort(chats: List<UserProfile>): List<UserProfile> {
        val mutableChats = chats.toMutableList()
        mutableChats.sortWith(ComparatorUserProfile())
        return mutableChats
    }

}