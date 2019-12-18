package media.pixi.appkit.domain.chats

import com.google.firebase.Timestamp
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.*
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.domain.ComparatorUserProfile
import media.pixi.appkit.ui.chat.MessageListItem
import media.pixi.appkit.ui.chat.MessageViewHolderType
import org.joda.time.DateTime
import java.lang.StringBuilder
import javax.inject.Inject

class GetChats @Inject constructor(private val chatProvider: ChatProvider,
                                   private val userProfileProvider: UserProfileProvider,
                                   private val authProvider: AuthProvider) {

    private inner class MyChatItemZipper: io.reactivex.functions.Function3<List<UserProfile>, ChatMessageEntity, MyChatStatus, ChatItem> {
        override fun apply(
            t1: List<UserProfile>,
            t2: ChatMessageEntity,
            t3: MyChatStatus
        ): ChatItem {
            return toChatItem(t1, t2, t3)
        }
    }

    private inner class MyChatZipper: io.reactivex.functions.Function3<List<MessageListItem>, ChatMessageEntity, ChatItem, Chat> {
        override fun apply(
            t1: List<MessageListItem>,
            t2: ChatMessageEntity,
            t3: ChatItem
        ): Chat {
            return toChat(t1, t2, t3)
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

    fun getChat(chatId: String): Flowable<Chat> {
        return Flowable.combineLatest(
            chatProvider.observerMessages(chatId)
                .map { toMessageListItems(it) },
            chatProvider.getMyChatStatus(chatId)
                .flatMap { maybeGetChatMessageEntity(chatId, it.lastSeenMessageId) },
            chatProvider.getChat(chatId)
                .toFlowable()
                .flatMap { getChatItem(it) },
            MyChatZipper()
        )
    }

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

    private fun toChat(messages: List<MessageListItem>, latestMassage: ChatMessageEntity, chatItem: ChatItem): Chat {
        return Chat(
            latestMessage = latestMassage,
            chatItem = chatItem,
            messages = messages
        )
    }

    private fun getNames(users: List<UserProfile>): String {
        val names = StringBuilder()
        for (user in users) {
            names.append(user.firstName)
        }

        return names.toString()
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
            isMe = isMe
        )
    }

}