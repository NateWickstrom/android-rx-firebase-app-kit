package media.pixi.appkit.domain.chats

import com.google.firebase.Timestamp
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.data.chats.ChatMessageRequest
import media.pixi.appkit.data.chats.ChatProvider
import media.pixi.appkit.data.notifications.NotificationEntity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.domain.notifications.MyNotification
import media.pixi.appkit.ui.chat.MessageListItem
import media.pixi.appkit.ui.chat.MessageViewHolderType
import org.joda.time.DateTime
import java.lang.StringBuilder
import java.util.*
import javax.inject.Inject

class GetChats @Inject constructor(private val chatProvider: ChatProvider,
                                   private val userProfileProvider: UserProfileProvider,
                                   private val authProvider: AuthProvider) {

    private inner class MyChatItemZipper: BiFunction<List<UserProfile>, ChatMessageEntity, ChatItem> {
        override fun apply(users: List<UserProfile>, message: ChatMessageEntity): ChatItem {
            return toChatItem(users, message)
        }
    }

    fun getChatItem(chatEntity: ChatEntity): Flowable<ChatItem> {
        val profiles = chatEntity.userIds.map { userProfileProvider.observerUserProfile(it) }

        return Flowable.zip(
            Flowable.zipIterable(profiles, { it.map { profile -> profile as UserProfile } },true, 1),
            chatProvider.getMessage(chatEntity.id, chatEntity.lastMessageId!!).toFlowable(),
            MyChatItemZipper()
        )
    }

    fun getChats(): Flowable<List<ChatEntity>> {
        return chatProvider.getChats()
    }

    fun getChat(chatId: String): Flowable<List<MessageListItem>> {
        return chatProvider.observerMessages(chatId)
            .map { toMessageListItems(it) }
    }

    fun hasChat(userIds: List<CharSequence>): Maybe<ChatEntity> {
        return chatProvider.hasChat(userIds.map { it.toString() })
    }

    fun sendMessage(message: String, chatId: String): Single<MessageListItem> {
        return chatProvider.sendMessage(chatId,
            ChatMessageRequest(
                text = message,
                senderId = authProvider.getUserId()!!,
                timestamp = Timestamp.now())
        ).map { toMessageListItem(it) }
    }

    fun createChat(message: String, userIds: List<CharSequence>): Single<ChatMessageEntity> {
        return chatProvider.createChat(
            ChatMessageRequest(
                text = message,
                senderId = authProvider.getUserId()!!,
                timestamp = Timestamp.now()),
            userIds.map { it.toString() }
        )
    }

    private fun toMessageListItems(messages: List<ChatMessageEntity>): List<MessageListItem> {
        return messages.map { toMessageListItem(it) }
    }

    private fun toChats(entities: List<ChatEntity>): List<Chat> {
        return entities.map { toChat(it) }
    }

    private fun toChat(entity: ChatEntity): Chat {
        return Chat(
            id = entity.id,
            title = entity.title ?: "no title",
            subtitle = "no subtitle"
        )
    }

    private fun toChatItem(users: List<UserProfile>, message: ChatMessageEntity): ChatItem {
        return ChatItem(
            title = getNames(users),
            subtitle = message.text,
            time = DateTime(message.timestamp.toDate()),
            hasSeen = true,
            profileImageUrls = users.map { it.imageUrl }
            )
    }

    private fun getNames(users: List<UserProfile>): String {
        val names = StringBuilder()
        for (user in users) {
            if (user.id.equals(authProvider.getUserId()!!).not()) {
                names.append(user.firstName)
            }
        }

        return names.toString()
    }

    private fun getImageUrls(users: List<UserProfile>): List<String> {
        return users
            .filter { it.id.equals(authProvider.getUserId()!!).not() }
            .map { it.imageUrl }
    }

    private fun toMessageListItem(message: ChatMessageEntity): MessageListItem {
        val textMessage = TextMessage(
            id = message.id,
            message = message.text,
            date = DateTime(message.timestamp.toDate()),
            senderId = message.senderId,
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )

        val sender = message.senderId
        val me = authProvider.getUserId()
        val isMe = me.equals(sender)
        val type = if (isMe) MessageViewHolderType.MY_TEXT else MessageViewHolderType.THEIR_TEXT

        return MessageListItem(
            message = textMessage,
            messageViewHolderType = type,
            sendIconUrl = "https://www.billboard.com/files/styles/article_main_image/public/media/Madonna-press-by-Ricardo-Gomes-2019-billboard-1548.jpg",
            isMe = isMe
        )
    }

}