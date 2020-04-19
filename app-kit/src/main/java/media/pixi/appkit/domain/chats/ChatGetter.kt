package media.pixi.appkit.domain.chats

import android.net.Uri
import com.google.firebase.Timestamp
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.*
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.data.storage.CloudStorageRepo
import media.pixi.appkit.domain.chats.models.*
import media.pixi.appkit.utils.StringUtils
import org.joda.time.DateTime
import java.io.File
import javax.inject.Inject

class ChatGetter @Inject constructor(private val chatProvider: ChatProvider,
                                     private val cloudStorageRepo: CloudStorageRepo,
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

    private inner class UploadZipper: io.reactivex.functions.BiFunction<Uri, Uri, UploadMetadata> {
        override fun apply(
            t1: Uri,
            t2: Uri
        ): UploadMetadata {
            return UploadMetadata(t1, t2)
        }
    }

    private data class UploadMetadata(
        val fileUrl: Uri,
        val thumbnailUri: Uri
        )

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

    fun sendMessage(message: String, attachments: List<MessageAttachment>, chatId: String): Single<MessageListItem> {
        val request = ChatMessageRequest(
            type = ChatMessageType.TEXT,
            text = message,
            senderId = authProvider.getUserId()!!,
            timestamp = Timestamp.now()
        )
        if (attachments.isNotEmpty()) {
            val requests = createAttachmentRequests(chatId, attachments)
            val allRequests = requests.toMutableList()
            if (message.isNotBlank()) {
                val messageRequest = chatProvider.sendMessage(chatId, request)
                allRequests.add(messageRequest)
            }

            return Single.zipArray({ list -> list.map { it as ChatMessageEntity }}, allRequests.toTypedArray())
                .map { toSingItem(it) }
        }
        return chatProvider.sendMessage(chatId, request)
            .map { toMessageListItem(it) }
    }

    private fun toSingItem(list: List<ChatMessageEntity>): MessageListItem {
        return toMessageListItem(list[0])
    }

    fun createChat(message: String, attachments: List<MessageAttachment>, userIds: List<CharSequence>): Single<ChatMessageEntity> {
        val request = ChatMessageRequest(
            type = ChatMessageType.TEXT,
            text = message,
            senderId = authProvider.getUserId()!!,
            timestamp = Timestamp.now()
        )

//        if (attachments.isNotEmpty()) {
//            val messageRequest = chatProvider.sendMessage(chatId, request)
//            val requests = createAttachmentRequests(chatId, attachments)
//            val allRequests = requests.toMutableList()
//            allRequests.add(messageRequest)
//
//            return Single.zipArray({ list -> list.map { it as ChatMessageEntity }}, allRequests.toTypedArray())
//                .map { toSingItem(it) }
//        }

        return chatProvider.createChat(
            request,
            userIds.map { it.toString() }
        )
    }

    fun getChatTitle(userIds: List<String>): Flowable<String> {
        return Flowable
            .combineLatest(userIds.map { userProfileProvider.observerUserProfile(it) })
                    { it.map { profile -> profile as UserProfile } }
            .map { toChatTitle(it) }
    }

    private fun createAttachmentRequests(chatId: String, attachments: List<MessageAttachment>): List<Single<ChatMessageEntity>> {
        return attachments.map { upload(chatId, it) }
    }

    private fun upload(chatId: String, attachment: MessageAttachment): Single<ChatMessageEntity> {
        val fileTask = cloudStorageRepo.addFile(chatId, attachment.fileId, File(attachment.fileUrl))
            .flatMap { cloudStorageRepo.getFile(chatId, attachment.fileId) }

        val thumbnailTask = cloudStorageRepo.addFile(chatId, attachment.thumbnailId, File(attachment.thumbnailUrl))
            .flatMap { cloudStorageRepo.getFile(chatId, attachment.thumbnailId) }

        return Single.zip(fileTask, thumbnailTask, UploadZipper())
            .map { metadata -> ChatMessageRequest(
                type = toChatAttachmentType(attachment.type),
                text = "",
                senderId = authProvider.getUserId()!!,
                timestamp = Timestamp.now(),
                thumbnailUrl = metadata.thumbnailUri.toString(),
                fileUrl = metadata.fileUrl.toString()
            ) }
            .flatMap {
                chatProvider.sendMessage(chatId, it)
            }
    }

    private fun maybeGetChatMessageEntity(chatId: String, messageId: String): Flowable<ChatMessageEntity> {
        val userId = authProvider.getUserId()!!
        return if (messageId.isBlank()) {
            // return a default message if missing
            Flowable.just(ChatMessageEntity(
                type = ChatMessageType.UNKNOWN,
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

    private fun toChatAttachmentType(attachments: MessageAttachmentType): ChatMessageType {
        return when (attachments) {
            MessageAttachmentType.IMAGE -> ChatMessageType.IMAGE
            MessageAttachmentType.VIDEO -> ChatMessageType.VIDEO
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
        return StringUtils.toChatTitle(
            profiles
                .filter { it.id.equals(authProvider.getUserId()!!).not() }
                .map { Pair(it.firstName, it.lastName) }
        )
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
            senderId = message.senderId,
            senderProfile = profiles[message.senderId],
            isMe = message.isMe
        )
    }

    private fun toMessageListItem(message: ChatMessageEntity): MessageListItem {
        val userMessage = when (message.type) {
            ChatMessageType.TEXT -> toTextMessage(message)
            ChatMessageType.IMAGE -> toImageMessage(message)
            ChatMessageType.VIDEO -> toVideoMessage(message)
            else -> toTextMessage(message)
        }

        val sender = message.senderId
        val me = authProvider.getUserId()
        val isMe = me.equals(sender)

        return MessageListItem(
            id = message.id,
            message = userMessage,
            sendIconUrl = "",
            senderId = message.senderId,
            senderProfile = null,
            isMe = isMe
        )
    }

    private fun toVideoMessage(message: ChatMessageEntity): VideoMessage {
        return VideoMessage(
            id = message.id,
            message = "<video>",
            date = DateTime(message.timestamp.toDate()),
            senderId = message.senderId,
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ,
            thumbnailUrl = message.thumbnailUrl!!,
            fileUrl = message.fileUrl!!
        )
    }

    private fun toImageMessage(message: ChatMessageEntity): ImageMessage {
        return ImageMessage(
            id = message.id,
            message = "<image>",
            date = DateTime(message.timestamp.toDate()),
            senderId = message.senderId,
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ,
            thumbnailUrl = message.thumbnailUrl!!,
            fileUrl = message.fileUrl!!
        )
    }

    private fun toTextMessage(message: ChatMessageEntity): TextMessage {
        return TextMessage(
            id = message.id,
            message = message.text,
            date = DateTime(message.timestamp.toDate()),
            senderId = message.senderId,
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )
    }

}