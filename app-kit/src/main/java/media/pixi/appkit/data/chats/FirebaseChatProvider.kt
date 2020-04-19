package media.pixi.appkit.data.chats

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*


class FirebaseChatProvider(): ChatProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun getChats(): Flowable<List<ChatEntity>> {
        val currentUserId = auth.currentUser!!.uid
        val ref = firestore
            .collection(MESSAGING)
            .whereArrayContains(THREAD_USERS, currentUserId)
//            .orderBy(THREAD_TIMESTAMP, Query.Direction.DESCENDING)

        return RxFirestore.observeQueryRef(ref)
            .map { toChatEntities(it.documents) }
            .map { sort(it) }
    }

    override fun getMyChatStatus(chatId: String): Flowable<MyChatStatus> {
        val currentUserId = auth.currentUser!!.uid
        val ref = firestore
            .collection(MESSAGING)
            .document(chatId)
            .collection(THREAD_USERS)
            .document(currentUserId)

        return Flowable.merge(
                RxFirestore.observeDocumentRef(ref).map { toMyChatStatus(it) },
                Flowable.just(MyChatStatus(lastSeenMessageId = ""))
            )
    }

    override fun getChat(chatId: String): Maybe<ChatEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(chatId)

        return RxFirestore.getDocument(ref)
            .map { toChatEntity(it) }
    }

    override fun hasChat(userIds: List<String>): Maybe<ChatEntity> {
        val hashcode = userHashcode(userIds)
        val ref = firestore
            .collection(MESSAGING)
            .whereEqualTo(THREAD_USERS_HASHCODE, hashcode)

        return RxFirestore.getCollection(ref)
            .map { toChatEntities(it.documents) }
            .flatMap { filter(it, userIds) }
    }

    override fun markAsLastSeen(chatId: String, messageId: String): Completable {
        val currentUserId = auth.currentUser!!.uid
        val ref = firestore
            .collection(MESSAGING)
            .document(chatId)
            .collection(THREAD_USERS)
            .document(currentUserId)

        val map = mutableMapOf<String, Any>()
        map[THREAD_LATEST_SEEN_MESSAGE] = messageId
        return RxFirestore.setDocument(ref, map)
    }

    override fun observerMessages(chatId: String): Flowable<List<ChatMessageEntity>> {
        val ref = firestore
            .collection(MESSAGING)
            .document(chatId)
            .collection(MESSAGES)
            .orderBy(THREAD_TIMESTAMP)

        return RxFirestore.observeQueryRef(ref).map { toMessageList(chatId, it) }
    }

    override fun sendMessage(
        chatId: String,
        message: ChatMessageRequest
    ): Single<ChatMessageEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(chatId)
            .collection(MESSAGES)

        return RxFirestore.addDocument(ref, toMap(message))
            .flatMap { RxFirestore.getDocument(it).toSingle() }
            .map { toChatMessage(chatId, it) }
    }

    override fun getMessage(chatId: String, messageId: String): Flowable<ChatMessageEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(chatId)
            .collection(MESSAGES)
            .document(messageId)

        return RxFirestore.observeDocumentRef(ref).map { toChatMessage(chatId, it) }
    }

    override fun getLatestMessage(chatId: String): Flowable<ChatMessageEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(chatId)
            .collection(MESSAGES)
            .orderBy(THREAD_TIMESTAMP, Query.Direction.DESCENDING)
            .limit(1)

        return RxFirestore.observeQueryRef(ref)
            .map { toMessageList(chatId, it) }
            .map { it[0] }
    }

    override fun createChat(initialMessage: ChatMessageRequest, userIds: List<String>): Single<ChatMessageEntity> {
        // create chat request
        val map = hashMapOf<String, Any>()
        map[THREAD_USERS] = userIds
        map[THREAD_USERS_HASHCODE] = userHashcode(userIds)

        // wait for chat id
        // send message to chat
        val ref = firestore
            .collection(MESSAGING)

        return RxFirestore.addDocument(ref, map)
            .map { it.id }
            .flatMap { sendMessage(it, initialMessage) }

    }

    private fun filter(chats: List<ChatEntity>, userIds: List<String>): Maybe<ChatEntity> {
        chats.forEach {
            if (equals(it.userIds, userIds)) {
                return Maybe.just(it)
            }
        }
        return Maybe.never()
    }

    private fun equals(list1: List<String>, list2: List<String>): Boolean {
        val set1 = hashSetOf<String>()
        set1.addAll(list1)

        val set2 = hashSetOf<String>()
        set2.addAll(list2)

        for (s in set1) {
            if (set2.contains(s).not())
                return false
        }

        return set1.size == set2.size
    }

    private fun sort(chats: List<ChatEntity>): List<ChatEntity> {
        val mutableChats = chats.toMutableList()
        mutableChats.sortWith(ComparatorChatEntity())
        mutableChats.reverse()
        return mutableChats
    }

    private fun getThreadMetadata(threadIds: List<String>): Flowable<List<ChatEntity>> {
        val metas = threadIds.map { getThreadMetadata(it) }

        return Flowable.zipIterable(metas, { list ->
            list.map { it as ChatEntity } }, true, 1)
    }

    private fun getThreadMetadata(threadId: String): Flowable<ChatEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(threadId)

        return RxFirestore.getDocument(ref)
            .toFlowable()
            .map { toChatEntity(it) }
    }

    private fun toMessageList(threadId: String, snapshot: QuerySnapshot): List<ChatMessageEntity> {
        return snapshot.documents.map { toChatMessage(threadId, it) }
    }

    private fun toMap(message: ChatMessageRequest): Map<String, Any> {
        val map = hashMapOf<String, Any>()
        map[MESSAGE_TYPE] = message.type.id
        map[MESSAGE_TEXT] = message.text
        map[MESSAGE_TIMESTAMP] = message.timestamp
        map[MESSAGE_SENDER_ID] = message.senderId
        if (message.thumbnailUrl.isNullOrEmpty().not()) {
            map[MESSAGE_THUMBNAIL_URL] = message.thumbnailUrl!!
        }
        if (message.fileUrl.isNullOrEmpty().not()) {
            map[MESSAGE_FILE_URL] = message.fileUrl!!
        }
        return map
    }

    private fun toStringsList(snapshot: QuerySnapshot): List<String> {
        return snapshot.documents.map { it.id }
    }

    private fun toChatMessage(chatId: String, snapshot: DocumentSnapshot): ChatMessageEntity {
        return ChatMessageEntity(
            type = toType(snapshot.getLong(MESSAGE_TYPE) ?: ChatMessageType.UNKNOWN.id),
            id = snapshot.id,
            chatId = chatId,
            text = snapshot.getString(MESSAGE_TEXT)!!,
            timestamp = snapshot.getTimestamp(MESSAGE_TIMESTAMP)!!,
            senderId = snapshot.getString(MESSAGE_SENDER_ID)!!,
            thumbnailUrl = snapshot.getString(MESSAGE_THUMBNAIL_URL),
            fileUrl = snapshot.getString(MESSAGE_FILE_URL)
        )
    }

    private fun toChatEntities(docs: List<DocumentSnapshot>): List<ChatEntity> {
        return docs.map { toChatEntity(it) }
    }

    private fun toChatEntity(snapshot: DocumentSnapshot): ChatEntity {
        return ChatEntity(
            id = snapshot.id,
            title = snapshot.getString(THREAD_TITLE),
            lastMessageId = snapshot.getString(THREAD_LATEST_MESSAGE),
            usersHashCode = snapshot.getLong(THREAD_USERS_HASHCODE)!!.toInt(),
            timestamp = snapshot.getTimestamp(THREAD_TIMESTAMP) ?: Timestamp.now(),
            userIds = snapshot.get(THREAD_USERS) as List<String> )
    }

    private fun toMyChatStatus(snapshot: DocumentSnapshot): MyChatStatus {
        return MyChatStatus(
            lastSeenMessageId = snapshot.getString(THREAD_LATEST_SEEN_MESSAGE) ?: ""
        )
    }

    private fun toType(code: Long): ChatMessageType {
        return when (code) {
            ChatMessageType.TEXT.id -> ChatMessageType.TEXT
            ChatMessageType.IMAGE.id -> ChatMessageType.IMAGE
            ChatMessageType.VIDEO.id -> ChatMessageType.VIDEO
            else -> ChatMessageType.UNKNOWN
        }
    }

    private fun userHashcode(userIds: List<String>): Int {
        var code = 0
        for (id in userIds) code += id.hashCode()
        return code
    }

    class ComparatorChatEntity: Comparator<ChatEntity> {
        override fun compare(c1: ChatEntity, c2: ChatEntity): Int {
            return c1.timestamp.compareTo(c2.timestamp)
        }
    }

    companion object {
        // COLLECTION   // DOC_IDS      // COLLECTIONS  // DOC_IDS
        // messaging    // threadIds    // messages     // messageIds
                                        // users        // userIds

        // Collections
        private const val MESSAGING = "messaging"
        private const val MESSAGES = "messages"

        // Thread Metadata
        private const val THREAD_TITLE = "title"
        private const val THREAD_USERS = "users"
        private const val THREAD_USERS_HASHCODE = "users_hashcode"
        private const val THREAD_TIMESTAMP = "timestamp"
        private const val THREAD_LATEST_MESSAGE = "latest_message_id"
        private const val THREAD_LATEST_SEEN_MESSAGE = "last_seen_message_id"

        // Message Metadata
        private const val MESSAGE_TYPE = "type"
        private const val MESSAGE_TEXT = "title"
        private const val MESSAGE_TIMESTAMP = "timestamp"
        private const val MESSAGE_SENDER_ID = "sender_id"
        private const val MESSAGE_THUMBNAIL_URL = "thumbnail"
        private const val MESSAGE_FILE_URL = "file"
    }
}