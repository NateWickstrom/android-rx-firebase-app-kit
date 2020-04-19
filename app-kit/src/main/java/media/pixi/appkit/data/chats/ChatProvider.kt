package media.pixi.appkit.data.chats

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface ChatProvider {

    fun getChats() : Flowable<List<ChatEntity>>

    fun getChat(chatId: String): Maybe<ChatEntity>

    fun getMyChatStatus(chatId: String): Flowable<MyChatStatus>

    fun hasChat(userIds: List<String>): Maybe<ChatEntity>

    fun markAsLastSeen(chatId: String, messageId: String): Completable

    fun observerMessages(chatId: String): Flowable<List<ChatMessageEntity>>

    fun sendMessage(chatId: String, message: ChatMessageRequest): Single<ChatMessageEntity>

    fun getMessage(chatId: String, messageId: String): Flowable<ChatMessageEntity>

    fun getLatestMessage(chatId: String): Flowable<ChatMessageEntity>

    fun createChat(initialMessage: ChatMessageRequest, userIds: List<String>): Single<ChatMessageEntity>
}