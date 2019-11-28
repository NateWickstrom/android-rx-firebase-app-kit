package media.pixi.appkit.domain.chats

import com.google.firebase.Timestamp
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.data.chats.ChatMessageRequest
import media.pixi.appkit.data.chats.ChatProvider
import media.pixi.appkit.ui.chat.MessageListItem
import media.pixi.appkit.ui.chat.MessageViewHolderType
import org.joda.time.DateTime
import javax.inject.Inject

class GetChats @Inject constructor(private val chatProvider: ChatProvider,
                                   private val authProvider: AuthProvider) {

    fun getChats(): Flowable<List<Chat>> {
        return chatProvider.getChats()
            .map { toChats(it) }
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

    private fun toMessageListItem(message: ChatMessageEntity): MessageListItem {
        val message2 = TextMessage(
            id = message.id,
            message = message.text,
            date = DateTime(),
            senderId = message.senderId,
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )
        val isMe = authProvider.getUserId() === message.senderId

        val messageListItem2 = MessageListItem(
            message = message2,
            messageViewHolderType = if (isMe)
                    MessageViewHolderType.MY_TEXT else MessageViewHolderType.THEIR_TEXT,
            sendIconUrl = "https://www.billboard.com/files/styles/article_main_image/public/media/Madonna-press-by-Ricardo-Gomes-2019-billboard-1548.jpg",
            isMe = isMe
        )

        return messageListItem2
    }

}