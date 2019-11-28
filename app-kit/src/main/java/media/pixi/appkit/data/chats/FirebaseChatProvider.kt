package media.pixi.appkit.data.chats

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*


class FirebaseChatProvider: ChatProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun getChats(): Flowable<List<ChatEntity>> {
        val currentUserId = auth.currentUser!!.uid
        val ref = firestore
            .collection(MESSAGING)
            .document(THREADS_METADATA)
            .collection(THREADS_METADATA)
            .whereArrayContains(THREAD_USERS, currentUserId)
//            .orderBy(THREAD_TIMESTAMP, Query.Direction.DESCENDING)

        return RxFirestore.getCollection(ref)
            .map { toChatEntities(it.documents) }
            .map { sort(it) }
            .toFlowable()
    }

    override fun getChat(chatId: String): Maybe<ChatEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(THREADS_METADATA)
            .collection(THREADS_METADATA)
            .document(chatId)

        return RxFirestore.getDocument(ref)
            .map { toChatEntity(it) }
    }

    override fun hasChat(userIds: List<String>): Maybe<ChatEntity> {
        val hashcode = userHashcode(userIds)
        val ref = firestore
            .collection(MESSAGING)
            .document(THREADS_METADATA)
            .collection(THREADS_METADATA)
            .whereEqualTo(THREAD_USERS_HASHCODE, hashcode)

        return RxFirestore.getCollection(ref)
            .map { toChatEntities(it.documents) }
            .flatMap { filter(it, userIds) }
    }

    override fun observerMessages(chatId: String): Flowable<List<ChatMessageEntity>> {
        val ref = firestore
            .collection(MESSAGING)
            .document(THREADS)
            .collection(MESSAGES)
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
            .document(THREADS)
            .collection(MESSAGES)
            .document(chatId)
            .collection(MESSAGES)

        return RxFirestore.addDocument(ref, toMap(message))
            .flatMap { RxFirestore.getDocument(it).toSingle() }
            .map { toChatMessage(chatId, it) }
    }

    override fun getMessage(chatId: String, messageId: String): Maybe<ChatMessageEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(THREADS_FOR_USERS)
            .collection(THREADS_FOR_USERS)
            .document(chatId)
            .collection(MESSAGES)
            .document(messageId)

        return RxFirestore.getDocument(ref).map { toChatMessage(chatId, it) }
    }

    override fun getLatestMessage(chatId: String): Flowable<ChatMessageEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(THREADS)
            .collection(MESSAGES)
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
            .document(THREADS_METADATA)
            .collection(THREADS_METADATA)

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
            .document(THREADS_METADATA)
            .collection(THREADS_METADATA)
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
        map[MESSAGE_TEXT] = message.text
        map[MESSAGE_TIMESTAMP] = message.timestamp
        map[MESSAGE_SENDER_ID] = message.senderId
        return map
    }

    private fun toStringsList(snapshot: QuerySnapshot): List<String> {
        return snapshot.documents.map { it.id }
    }

    private fun toChatMessage(chatId: String, snapshot: DocumentSnapshot): ChatMessageEntity {
        return ChatMessageEntity(
            id = snapshot.id,
            chatId = chatId,
            text = snapshot.getString(MESSAGE_TEXT)!!,
            timestamp = snapshot.getTimestamp(MESSAGE_TIMESTAMP)!!,
            senderId = snapshot.getString(MESSAGE_SENDER_ID)!!
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
        // COLLECTION// DOC_IDS             // COLLECTIONS          // DOC_IDS      // COLLECTIONS      // DOC_IDS
        // messaging // threads             // threads              // threadIds    // messages         // messageIds
        // messaging // threads_meta        // threads_meta         // threadIds    // metadata (DOC)
        // messaging // threads_for_users   // threads_for_users    // userId       // threads          // threadIds
        // messaging // threads_create_req  // threads_create_req   // requestId    // initialize (DOC)

        private const val MESSAGING = "messaging"
        private const val MESSAGES = "messages"
        private const val THREADS = "threads"
        private const val THREADS_METADATA = "threads_meta"
        private const val THREADS_FOR_USERS = "threads_for_users"
        private const val THREADS_CREATION_REQUEST = "threads_creation_request"

        private const val THREAD_TITLE = "title"
        private const val THREAD_USERS = "users"
        private const val THREAD_USERS_HASHCODE = "users_hashcode"
        private const val THREAD_TIMESTAMP = "timestamp"
        private const val THREAD_LATEST_MESSAGE = "latest_message_id"

        private const val MESSAGE_TEXT = "title"
        private const val MESSAGE_TIMESTAMP = "timestamp"
        private const val MESSAGE_SENDER_ID = "sender_id"

    }
}