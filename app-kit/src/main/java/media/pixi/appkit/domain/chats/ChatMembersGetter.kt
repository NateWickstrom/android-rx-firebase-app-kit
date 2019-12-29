package media.pixi.appkit.domain.chats

import io.reactivex.Flowable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.ChatProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.domain.users.ComparatorUserProfile
import javax.inject.Inject

class ChatMembersGetter @Inject constructor(private val chatProvider: ChatProvider,
                                            private val userProfileProvider: UserProfileProvider,
                                            private val authProvider: AuthProvider
) {

    fun getChatMembers(chatId: String): Flowable<List<UserProfile>> {
        return chatProvider.getChat(chatId)
            .toFlowable()
            .concatMap { toProfiles(it.userIds) }
            .map { sort(it) }
    }

    private fun toProfiles(userIds: List<String>) : Flowable<List<UserProfile>> {
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