package media.pixi.appkit.domain.chats

import io.reactivex.Flowable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.data.chats.ChatProvider
import media.pixi.appkit.data.chats.MyChatStatus
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import org.joda.time.DateTime
import java.lang.StringBuilder
import javax.inject.Inject

class ChatListItemGetter @Inject constructor(private val chatProvider: ChatProvider,
                                             private val userProfileProvider: UserProfileProvider,
                                             private val authProvider: AuthProvider
) {

    private inner class MyChatItemZipper: io.reactivex.functions.Function3<List<UserProfile>, ChatMessageEntity, MyChatStatus, ChatItem> {
        override fun apply(
            t1: List<UserProfile>,
            t2: ChatMessageEntity,
            t3: MyChatStatus
        ): ChatItem {
            return toChatItem(t1, t2, t3)
        }
    }

    fun getChatItem(chatEntity: ChatEntity): Flowable<ChatItem> {
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

    private fun toChatItem(users: List<UserProfile>, message: ChatMessageEntity, myChatStatus: MyChatStatus): ChatItem {
        val seen = myChatStatus.lastSeenMessageId.equals(message.id)

        val otherUsers = users.filter { it.id.equals(authProvider.getUserId()!!).not() }

        return ChatItem(
            title = getNames(otherUsers),
            subtitle = message.text,
            time = DateTime(message.timestamp.toDate()),
            hasSeen = seen,
            users = otherUsers
        )
    }

    private fun getNames(users: List<UserProfile>): String {
        val names = StringBuilder()
        for (user in users) {
            names.append(user.firstName)
        }

        return names.toString()
    }
}