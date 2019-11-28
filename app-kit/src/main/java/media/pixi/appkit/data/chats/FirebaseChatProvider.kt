package media.pixi.appkit.data.chats

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
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
            .document(THREADS_FOR_USERS)
            .collection(THREADS_FOR_USERS)
            .document(currentUserId)
            .collection(THREADS)

        return RxFirestore.getCollection(ref)
            .toFlowable()
            .map { toStringsList(it) }
            .flatMap { getThreadMetadata(it) }
            .map { sort(it) }
    }

    override fun observerMessages(chatId: String): Flowable<List<ChatMessageEntity>> {
        val ref = firestore
            .collection(MESSAGING)
            .document(THREADS_FOR_USERS)
            .collection(THREADS_FOR_USERS)
            .document(chatId)
            .collection(MESSAGES)
            .orderBy(THREAD_TIMESTAMP)

        return RxFirestore.observeQueryRef(ref).map { toMessageList(it) }
    }

    override fun sendMessage(
        chatId: String,
        message: ChatMessageRequest
    ): Single<ChatMessageEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(THREADS_FOR_USERS)
            .collection(THREADS_FOR_USERS)
            .document(chatId)
            .collection(MESSAGES)

        return RxFirestore.addDocument(ref, toMap(message))
            .flatMap { RxFirestore.getDocument(it).toSingle() }
            .map { toChatMessage(it) }
    }

    override fun getMessage(chatId: String, messageId: String): Maybe<ChatMessageEntity> {
        val ref = firestore
            .collection(MESSAGING)
            .document(THREADS_FOR_USERS)
            .collection(THREADS_FOR_USERS)
            .document(chatId)
            .collection(MESSAGES)
            .document(messageId)

        return RxFirestore.getDocument(ref).map { toChatMessage(it) }
    }

    override fun createChat(initialMessage: ChatMessageRequest, userIds: List<String>): Single<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        // create chat request
        // wait for chat id
        // send message to chat
    }

    private fun sort(chats: List<ChatEntity>): List<ChatEntity> {
        val mutableChats = chats.toMutableList()
        mutableChats.sortWith(ComparatorChatEntity())
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

    private fun toMessageList(snapshot: QuerySnapshot): List<ChatMessageEntity> {
        return snapshot.documents.map { toChatMessage(it) }
    }

    private fun toMap(message: ChatMessageRequest): Map<String, Any> {
        val map = hashMapOf<String, Any>()
        return map
    }

    private fun toStringsList(snapshot: QuerySnapshot): List<String> {
        return snapshot.documents.map { it.id }
    }

    private fun toChatMessage(snapshot: DocumentSnapshot): ChatMessageEntity {
        return ChatMessageEntity(
            id = snapshot.id
        )
    }

    private fun toChatEntity(snapshot: DocumentSnapshot): ChatEntity {
        return ChatEntity(
            id = snapshot.id,
            title = snapshot.getString(THREAD_TITLE),
            lastMessageId = snapshot.getString(THREAD_LATEST_MESSAGE)!!,
            usersHashCode = snapshot.getLong(THREAD_USERS_HASHCODE)!!.toInt(),
            timestamp = snapshot.getTimestamp(THREAD_TIMESTAMP) ?: Timestamp.now(),
            userIds = snapshot.get(THREAD_USERS) as List<String> )
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
    }
}