package media.pixi.appkit.data.chats

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface ChatProvider {

    fun getChats() : Flowable<List<ChatEntity>>

    fun observerMessages(chatId: String): Flowable<List<ChatMessageEntity>>

    fun sendMessage(chatId: String, message: ChatMessageEntity): Single<ChatMessageEntity>

    fun getMessage(chatId: String, messageId: String): Maybe<ChatMessageEntity>

    fun createChat(initialMessage: ChatMessageEntity, userIds: List<String>): Single<Int>
}