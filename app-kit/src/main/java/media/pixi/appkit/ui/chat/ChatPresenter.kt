package media.pixi.appkit.ui.chat

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.provider.MediaStore
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.data.chats.ChatProvider
import media.pixi.appkit.data.drafts.Draft
import media.pixi.appkit.data.drafts.DraftAttachment
import media.pixi.appkit.data.drafts.DraftAttachmentType
import media.pixi.appkit.data.files.FileProvider
import media.pixi.appkit.data.storage.CloudStorageRepo
import media.pixi.appkit.domain.chats.ChatGetter
import media.pixi.appkit.domain.chats.MessageBus
import media.pixi.appkit.domain.chats.models.*
import media.pixi.appkit.domain.drafs.DraftHelper
import timber.log.Timber
import java.io.File
import java.util.*
import javax.inject.Inject

class ChatPresenter @Inject constructor(
    private val navigator: ChatContract.Navigator,
    private var chatProvider: ChatProvider,
    private val chatsGetter: ChatGetter,
    private val draftHelper: DraftHelper,
    private val fileProvider: FileProvider,
    private val cloudStorageRepo: CloudStorageRepo,
    private val messageBus: MessageBus
) : ChatContract.Presenter, MessageBus.MessageListener {

    private inner class ImageZipper: io.reactivex.functions.BiFunction<String, String, MessageAttachment> {
        override fun apply(
            t1: String,
            t2: String
        ): MessageAttachment {
            return  MessageAttachment(
                fileId = UUID.randomUUID().toString(),
                thumbnailId = UUID.randomUUID().toString(),
                type = MessageAttachmentType.IMAGE,
                thumbnailUrl = t1,
                fileUrl = t2
            )
        }
    }

    private inner class VideoZipper: io.reactivex.functions.BiFunction<String, String, MessageAttachment> {
        override fun apply(
            t1: String,
            t2: String
        ): MessageAttachment {
            return  MessageAttachment(
                fileId = UUID.randomUUID().toString(),
                thumbnailId = UUID.randomUUID().toString(),
                type = MessageAttachmentType.VIDEO,
                thumbnailUrl = t1,
                fileUrl = t2
            )
        }
    }

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

    override fun send(text: String, attachments: List<MessageAttachment>) {
        if (text.isBlank() && attachments.isEmpty()) {
            return
        }

        view?.loading = true

        if (chatId.isNullOrBlank()) {
            disposables.addAll(
                chatsGetter.createChat(text, attachments, userIds!!)
                    .subscribe(
                        { onChatCreated(it.chatId) },
                        { onError(it) }
                    )
            )
        } else {
            disposables.add(
                chatsGetter.sendMessage(text, attachments, chatId!!)
                    .subscribe(
                        { onMessageSent(it) },
                        { onError(it) }
                    )
            )
        }
    }

    override fun saveDraft(text: String, attachments: List<MessageAttachment>) {
        chatId?.let {
            draftHelper.saveDraft(
                    Draft(
                        id = it,
                        text = text,
                        attachments = toDraftAttachments(attachments)
                    )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
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
        return this.chatId === chatId
    }

    override fun onAttachmentClicked(activity: Activity, attachment: MessageAttachment) {
        when (attachment.type) {
            MessageAttachmentType.IMAGE -> navigator.showImage(activity, "file://" + attachment.fileUrl)
            MessageAttachmentType.VIDEO -> navigator.showVideo(activity, attachment.fileUrl)
        }
    }

    override fun onAttachmentDeleteClicked(position: Int, attachment: MessageAttachment) {
        disposables.addAll(
            //TODO error handling
            fileProvider.delete(attachment.fileUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(),
            fileProvider.delete(attachment.thumbnailUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(),
            draftHelper.removeAttachment(toDraftAttachment(attachment))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun onImageSelected(path: String) {
        Timber.d("Image: $path")

        val thumbnailId = UUID.randomUUID().toString()
        val imageId = UUID.randomUUID().toString()

        disposables.add(
            Single.zip(
                Single.fromCallable { createImageThumbnail(path) }
                    .flatMap { fileProvider.add(chatId!!, thumbnailId, it) },
                fileProvider.add(chatId!!, imageId, File(path)),
                ImageZipper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { addAttachment(it) },
                    { onErrorSeen(it) }
                )
        )
    }

    override fun onVideoSelected(path: String) {
        Timber.d("Video: $path")

        val thumbnailId = UUID.randomUUID().toString()
        val imageId = UUID.randomUUID().toString()

        disposables.add(
            Single.zip(
                    Single.fromCallable { createVideoThumbnail(path) }
                        .flatMap { fileProvider.add(chatId!!, thumbnailId, it) },
                    fileProvider.add(chatId!!, imageId, File(path)),
                    VideoZipper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { addAttachment(it) },
                    { onErrorSeen(it) }
                )
        )
    }

    private fun onCompletedSeen() {

    }

    private fun onErrorSeen(error: Throwable) {
        Timber.e(error)
    }

    private fun onChatCreated(chatId: String) {
        this.chatId = chatId
        view?.clearAttachments()

        disposables.addAll(
            chatsGetter.getChat(chatId)
                .subscribe(
                    { onSubscribedToChat(it) },
                    { onError(it) }
                ),
            draftHelper.deleteDraft(chatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
            )
    }

    private fun onFoundChatId(chatId: String) {
        this.chatId = chatId
        disposables.addAll(
            chatsGetter.getChat(chatId)
                .subscribe(
                    { onSubscribedToChat(it) },
                    { onError(it) }
                ),
            draftHelper.getDraft(chatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                    { onDraftLoaded(it) },
                    { onError(it) }
                )
        )
    }

    private fun onDraftLoaded(draft: Draft) {
        draft.text?.let {
            view?.text = it
        }
        view?.addAttachments(toMessageAttachments(draft.attachments))
    }

    private fun onMessageSent(message: MessageListItem) {
        view?.scrollToEnd()
        view?.clearAttachments()
        view?.loading = false

        disposables.add(
            draftHelper.deleteDraft(chatId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
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
        Timber.e(error)
        view?.error = "Oops, something when wrong"
    }

    private fun addAttachment(attachment: MessageAttachment) {
        view?.addAttachment(attachment)
    }

    private fun createVideoThumbnail(url: String): Bitmap {
        return ThumbnailUtils.createVideoThumbnail(
            url,
            MediaStore.Video.Thumbnails.MICRO_KIND
        )!!
    }

    private fun createImageThumbnail(url: String): Bitmap {
        return ThumbnailUtils.extractThumbnail(
            BitmapFactory.decodeFile(url),
            THUMB_SIZE,
            THUMB_SIZE
        )
    }

    private fun toDraftAttachments(attachments: List<MessageAttachment>): List<DraftAttachment>  {
        return attachments.map {
            toDraftAttachment(it)
        }
    }

    private fun toMessageAttachments(attachments: List<DraftAttachment>): List<MessageAttachment>  {
        return attachments.map {
            MessageAttachment(
                fileId = it.id,
                thumbnailId = UUID.randomUUID().toString(),
                type = toMessageAttachmentType(it.type),
                thumbnailUrl = it.thumbnailUrl,
                fileUrl = it.fileUrl
            )
        }
    }

    private fun toMessageAttachmentType(type: DraftAttachmentType): MessageAttachmentType {
        return when (type) {
            DraftAttachmentType.IMAGE -> MessageAttachmentType.IMAGE
            DraftAttachmentType.VIDEO -> MessageAttachmentType.VIDEO
        }
    }

    private fun toDraftAttachmentType(type: MessageAttachmentType): DraftAttachmentType {
        return when (type) {
            MessageAttachmentType.IMAGE -> DraftAttachmentType.IMAGE
            MessageAttachmentType.VIDEO -> DraftAttachmentType.VIDEO
        }
    }

    private fun toDraftAttachment(attachment: MessageAttachment): DraftAttachment {
        return DraftAttachment(
            id = attachment.fileId,
            type = toDraftAttachmentType(attachment.type),
            thumbnailUrl = attachment.thumbnailUrl,
            fileUrl = attachment.fileUrl
        )
    }

    companion object {
        private const val THUMB_SIZE = 96
    }
}