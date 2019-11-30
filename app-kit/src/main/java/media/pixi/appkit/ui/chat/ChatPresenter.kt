package media.pixi.appkit.ui.chat

import android.app.Activity
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.chats.ChatProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.domain.chats.*
import timber.log.Timber
import javax.inject.Inject

class ChatPresenter @Inject constructor(private val navigator: ChatContract.Navigator,
                                        private val authProvider: AuthProvider,
                                        private var chatProvider: ChatProvider,
                                        private val chatsGetter: GetChats) : ChatContract.Presenter {

    private var view: ChatContract.View? = null
    private var results = mutableListOf<MessageListItem>()
    private var disposables = CompositeDisposable()

    var chatId: String? = null
    var userIds: List<CharSequence>? = null

    var id = 5

    override fun takeView(view: ChatContract.View) {
        this.view = view
        view.loading = true
        view.canSend = false

        chatId?.let { id ->
            disposables.add(
                chatsGetter.getChat(id).subscribe(
                    { onSubscribedToChat(it) },
                    { onError(it) }
                )
            )
        }
        userIds?.let { ids ->
            disposables.add(
                chatsGetter.hasChat(ids).subscribe(
                    { onFoundChatId(it.id) },
                    { onError(it) },
                    { onNoChatIdFound() }
                )
            )
        }

    }

    override fun dropView() {
        view = null
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

    override fun onItemsViewed(firstPosition: Int, lastPosition: Int) {
        val message = results[lastPosition]
        val myUserId = authProvider.getUserId()!!
        if (message.hasSeen.contains(myUserId)) return

        disposables.add(
            chatProvider.markAsLastSeen(chatId!!, results[lastPosition].id).subscribe(
                { onCompletedSeen() },
                { onErrorSeen(it) }
            )
        )
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
        view?.loading = false

    }

    private fun onSubscribedToChat(results: List<MessageListItem>) {
        this.results = results.toMutableList()
        view?.loading = false
        view?.canSend = true
        view?.setResults(results)
    }

    private fun onNoChatIdFound() {
        results = mutableListOf()
        view?.loading = false
        view?.canSend = true

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