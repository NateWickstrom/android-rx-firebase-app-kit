package media.pixi.appkit.domain.chats

import io.reactivex.Flowable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.data.chats.ChatProvider
import media.pixi.appkit.data.chats.MyChatStatus
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.domain.chats.models.ChatListItem
import media.pixi.appkit.utils.StringUtils
import org.joda.time.DateTime
import javax.inject.Inject

class ChatListItemsGetter @Inject constructor(private val chatProvider: ChatProvider,
                                              private val userProfileProvider: UserProfileProvider,
                                              private val authProvider: AuthProvider
) {

    private inner class MyChatItemZipper: io.reactivex.functions.Function3<List<UserProfile>, ChatMessageEntity, MyChatStatus, ChatListItem> {
        override fun apply(
            t1: List<UserProfile>,
            t2: ChatMessageEntity,
            t3: MyChatStatus
        ): ChatListItem {
            return toChatItem(t1, t2, t3)
        }
    }

    fun getChatItem(chatEntity: ChatEntity): Flowable<ChatListItem> {
        if (chatEntity.lastMessageId == null) return Flowable.never()

        val profiles = chatEntity.userIds.map { userProfileProvider.observerUserProfile(it) }

        return Flowable.combineLatest(
            Flowable.combineLatest(profiles, { it.map { profile -> profile as UserProfile } }, 1),
            chatProvider.getMessage(chatEntity.id, chatEntity.lastMessageId),
            chatProvider.getMyChatStatus(chatEntity.id),
            MyChatItemZipper()
        )
    }

    fun getChats(): Flowable<List<ChatEntity>> {
        return chatProvider.getChats()
    }

    private fun toChatItem(users: List<UserProfile>, message: ChatMessageEntity, myChatStatus: MyChatStatus): ChatListItem {
        val seen = myChatStatus.lastSeenMessageId.equals(message.id)

        val otherUsers = users.filter { it.id.equals(authProvider.getUserId()!!).not() }

        return ChatListItem(
            title = getNames(otherUsers),
            subtitle = message.text,
            time = DateTime(message.timestamp.toDate()),
            hasSeen = seen,
            users = otherUsers
        )
    }

    private fun getNames(users: List<UserProfile>): String {
        return StringUtils.toChatTitle(
            users
                .filter { it.id.equals(authProvider.getUserId()!!).not() }
                .map { Pair(it.firstName, it.lastName) }
        )
    }
}