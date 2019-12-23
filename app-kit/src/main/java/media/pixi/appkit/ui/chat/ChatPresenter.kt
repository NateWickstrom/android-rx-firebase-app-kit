package media.pixi.appkit.ui.chat

import android.app.Activity
import android.media.ThumbnailUtils
import android.provider.MediaStore
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.data.chats.ChatProvider
import media.pixi.appkit.domain.chats.ChatGetter
import media.pixi.appkit.domain.chats.MessageBus
import media.pixi.appkit.domain.chats.models.*
import timber.log.Timber
import javax.inject.Inject

class ChatPresenter @Inject constructor(
    private val navigator: ChatContract.Navigator,
    private val authProvider: AuthProvider,
    private var chatProvider: ChatProvider,
    private val chatsGetter: ChatGetter,
    private val messageBus: MessageBus
) : ChatContract.Presenter, MessageBus.MessageListener {

    private var view: ChatContract.View? = null
    private var results = mutableListOf<MessageListItem>()
    private var disposables = CompositeDisposable()

    var chatId: String? = null
    var userIds: List<CharSequence>? = null
    var latestMessage: ChatMessageEntity? = null

    override fun takeView(view: ChatContract.View) {
        this.view = view
        view.loading = true
        view.canSend = false
        view.title = ""

        chatId?.let { id ->
            onFoundChatId(id)
        }
        userIds?.let { ids ->
            disposables.add(
                chatsGetter.getChatId(ids).subscribe(
                    { onFoundChatId(it) },
                    { onError(it) },
                    { onNoChatIdFound() }
                )
            )
        }
        messageBus.addListener(this)
    }

    override fun dropView() {
        view = null
        messageBus.removeListener(this)
        disposables.clear()
    }

    override fun send(text: String) {
        view?.loading = true

        if (chatId.isNullOrBlank()) {
            disposables.add(
                chatsGetter.createChat(text, userIds!!).subscribe(
                    { onFoundChatId(it.chatId) },
                    { onError(it) }
                )
            )
        } else {
            disposables.add(
                chatsGetter.sendMessage(text, chatId!!).subscribe(
                    { onMessageSent(it) },
                    { onError(it) }
                )
            )
        }
    }

    override fun onTextClicked(position: Int, item: MessageListItem) {
        view?.showTextSpeedDial(item)
    }

    override fun onImageClicked(position: Int, item: MessageListItem) {

    }

    override fun onLocationClicked(position: Int, item: MessageListItem) {
        view?.showLocationSpeedDial(item)
    }

    override fun onOptionsClicked(activity: Activity) {
        navigator.showOptions(activity)
    }

    override fun onShowChatMembersClicked(activity: Activity) {
        navigator.showChatMembers(activity, chatId!!)
    }

    override fun onItemsViewed(firstPosition: Int, lastPosition: Int) {
        val message = results[lastPosition]

        if (message.timeInMillis <= latestMessage!!.timestamp.toDate().time)
            return

        disposables.add(
            chatProvider.markAsLastSeen(chatId!!, results[lastPosition].id).subscribe(
                { onCompletedSeen() },
                { onErrorSeen(it) }
            )
        )
    }

    override fun onMessageReceived(chatId: String, message: Message): Boolean {
        return chatId.equals(this.chatId)
    }

    override fun onAttachmentClicked(position: Int, attachment: MessageAttachment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAttachmentDeleteClicked(position: Int, attachment: MessageAttachment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onImageSelected(path: String) {
//        ThumbnailUtils.createVideoThumbnail(
//            path,
//            MediaStore.Video.Thumbnails.MINI_KIND
//        )
        view?.addAttachment(MessageAttachment(
            id = "1234",
            type = MessageAttachmentType.MY_IMAGE,
            imageUrl = path
        ))
//        Timber.d("Image: $path")
    }

    override fun onVideoSelected(path: String) {
        //view?.showVideoAttachment(path)
        Timber.d("Video: $path")
    }

    private fun onCompletedSeen() {

    }

    private fun onErrorSeen(error: Throwable) {

    }

    private fun onFoundChatId(chatId: String) {
        this.chatId = chatId
        disposables.add(
            chatsGetter.getChat(chatId).subscribe(
                { onSubscribedToChat(it) },
                { onError(it) }
            )
        )
    }

    private fun onMessageSent(message: MessageListItem) {
        view?.scrollToEnd()
        view?.loading = false
    }

    private fun onSubscribedToChat(chat: Chat) {
        this.results = chat.messages.toMutableList()
        latestMessage = chat.latestMessage
        view?.loading = false
        view?.canSend = true
        view?.title = chat.title
        view?.setResults(results)
    }

    private fun onChatTitleCreated(title: String) {
        results = mutableListOf()
        view?.title = title
        view?.loading = false
        view?.canSend = true
    }

    private fun onNoChatIdFound() {
        disposables.add(
            chatsGetter.getChatTitle(userIds!!.map { it.toString() }).subscribe(
                { onChatTitleCreated(it) },
                { onError(it) }
            )
        )
    }

    private fun onError(error: Throwable) {
        results = mutableListOf()
        view?.loading = false
        view?.canSend = false
        Timber.e(TAG, error)
        view?.error = "Oops, something when wrong"
    }

    companion object {
        const val TAG = "ChatPresenter"
    }
}