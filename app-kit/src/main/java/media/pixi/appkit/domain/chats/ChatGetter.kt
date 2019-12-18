package media.pixi.appkit.domain.chats

import com.google.firebase.Timestamp
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.*
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.ui.chat.MessageListItem
import media.pixi.appkit.ui.chat.MessageViewHolderType
import org.joda.time.DateTime
import java.lang.StringBuilder
import javax.inject.Inject

class ChatGetter @Inject constructor(private val chatProvider: ChatProvider,
                                     private val userProfileProvider: UserProfileProvider,
                                     private val authProvider: AuthProvider) {

    private inner class MyChatZipper: io.reactivex.functions.Function3<List<MessageListItem>, ChatMessageEntity, List<UserProfile>, Chat> {
        override fun apply(
            t1: List<MessageListItem>,
            t2: ChatMessageEntity,
            t3: List<UserProfile>
        ): Chat {
            return toChat(t1, t2, t3)
        }
    }

    fun getChat(chatId: String): Flowable<Chat> {
        return chatProvider.getChat(chatId)
            .toFlowable()
            .concatMap {
                Flowable.combineLatest(
                    chatProvider.observerMessages(chatId)
                        .map { toMessageListItems(it) },
                    chatProvider.getMyChatStatus(chatId)
                        .flatMap { maybeGetChatMessageEntity(chatId, it.lastSeenMessageId) },
                    Flowable.combineLatest(
                        it.userIds.map { userProfileProvider.observerUserProfile(it) }
                    ) { it.map { profile -> profile as UserProfile } },
                    MyChatZipper()
                )
            }

    }

    fun getChatId(userIds: List<CharSequence>): Maybe<String> {
        return chatProvider.hasChat(userIds.map { it.toString() }).map { it.id }
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

    private fun maybeGetChatMessageEntity(chatId: String, messageId: String): Flowable<ChatMessageEntity> {
        val userId = authProvider.getUserId()!!
        return if (messageId.isBlank()) {
            // return a default message if missing
            Flowable.just(ChatMessageEntity(
                id = "",
                chatId = chatId,
                text = "",
                timestamp = Timestamp(0,0),
                senderId = userId
            ))
        } else {
            chatProvider.getMessage(chatId, messageId)
        }
    }

    private fun toMessageListItems(messages: List<ChatMessageEntity>): List<MessageListItem> {
        return messages.map { toMessageListItem(it) }
    }

    private fun toChat(messages: List<MessageListItem>, latestMassage: ChatMessageEntity, profiles: List<UserProfile>): Chat {
        return Chat(
            latestMessage = latestMassage,
            title = toChatTitle(profiles),
            users = profiles,
            messages = toProperMessages(messages, profiles)
        )
    }

    private fun toChatTitle(profiles: List<UserProfile>): String {
        val sb = StringBuilder()

        profiles
            .filter { it.id.equals(authProvider.getUserId()!!).not() }
            .forEach { sb.append(it.firstName + ", ") }

        sb.removeSuffix(", ")
        return sb.toString()
    }

    private fun toProperMessages(messages: List<MessageListItem>, profiles: List<UserProfile>): List<MessageListItem> {
        val set = mutableMapOf<String, UserProfile>()
        profiles.forEach {
            set[it.id] = it
        }
        return messages.map { toProperMessage(it, set) }
    }

    private fun toProperMessage(message: MessageListItem, profiles: Map<String, UserProfile>): MessageListItem {
        return MessageListItem(
            id = message.id,
            message = message.message,
            messageViewHolderType = message.messageViewHolderType,
            sendIconUrl = profiles[message.senderId]?.imageUrl!!,
            senderId = message.senderId,
            isMe = message.isMe
        )
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
            id = message.id,
            message = textMessage,
            messageViewHolderType = type,
            sendIconUrl = "https://www.billboard.com/files/styles/article_main_image/public/media/Madonna-press-by-Ricardo-Gomes-2019-billboard-1548.jpg",
            senderId = message.senderId,
            isMe = isMe
        )
    }

}