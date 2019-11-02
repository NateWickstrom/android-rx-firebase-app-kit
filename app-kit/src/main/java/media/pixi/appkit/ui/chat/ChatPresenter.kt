package media.pixi.appkit.ui.chat

import media.pixi.appkit.domain.chats.ImageMessage
import media.pixi.appkit.domain.chats.MessageReadStatus
import media.pixi.appkit.domain.chats.MessageSendStatus
import media.pixi.appkit.domain.chats.TextMessage
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

class ChatPresenter @Inject constructor() : ChatContract.Presenter {

    private var view: ChatContract.View? = null
    private var results = mutableListOf<MessageListItem>()

    var id = 5

    override fun takeView(view: ChatContract.View) {
        this.view = view
        view.loading = true
        showFakeChat()
    }

    override fun dropView() {
        view = null
    }

    override fun send(text: String) {
        val message2 = TextMessage(
            id = id.toString(),
            message = text,
            date = DateTime(),
            senderId = "2",
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )
        val messageListItem2 = MessageListItem(
            message = message2,
            messageViewHolderType = MessageViewHolderType.MY_TEXT,
            sendIconUrl = "https://www.billboard.com/files/styles/article_main_image/public/media/Madonna-press-by-Ricardo-Gomes-2019-billboard-1548.jpg",
            isMe = true
        )

        id++
        results.add(messageListItem2)
        onResult(results)
    }

    override fun onTextClicked(position: Int, item: MessageListItem) {
        view?.showTextSpeedDial(item)
    }

    override fun onImageClicked(position: Int, item: MessageListItem) {
        view?.showImageSpeedDial(item)
    }

    override fun onLocationClicked(position: Int, item: MessageListItem) {
        view?.showLocationSpeedDial(item)
    }

    private fun onResult(results: List<MessageListItem>) {
        view?.loading = false
        view?.setResults(results)
    }

    private fun onError(error: Throwable) {
        view?.loading = false
        Timber.e(TAG, error)
        view?.error = "Oops, somehting when wrong"
    }

    private fun showFakeChat() {
        results.clear()

        val message1 = TextMessage(
            id = "1",
            message = "That's a true story.",
            date = DateTime(),
            senderId = "1",
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )
        val messageListItem1 = MessageListItem(
            message = message1,
            messageViewHolderType = MessageViewHolderType.THEIR_TEXT,
            sendIconUrl = "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTE1ODA0OTcxNzkzMjIxMTMz/sting-9495433-1-402.jpg"
        )

        val message2 = TextMessage(
            id = "2",
            message = "What I want...",
            date = DateTime(),
            senderId = "2",
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )
        val messageListItem2 = MessageListItem(
            message = message2,
            messageViewHolderType = MessageViewHolderType.MY_TEXT,
            sendIconUrl = "https://www.billboard.com/files/styles/article_main_image/public/media/Madonna-press-by-Ricardo-Gomes-2019-billboard-1548.jpg",
            isMe = true
        )

        val message3 = TextMessage(
            id = "3",
            message = "Friendship is everything. Friendship is more than talent. It is more than the government. It is almost the equal of family.",
            date = DateTime(),
            senderId = "1",
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )
        val messageListItem3 = MessageListItem(
            message = message3,
            messageViewHolderType = MessageViewHolderType.THEIR_TEXT,
            sendIconUrl = "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTE1ODA0OTcxNzkzMjIxMTMz/sting-9495433-1-402.jpg"
        )

        val message4 = TextMessage(
            id = "4",
            message = "What I want... what's most important to me is that I have a guarantee. No more attempts on my father's life.",
            date = DateTime(),
            senderId = "2",
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )
        val messageListItem4 = MessageListItem(
            message = message4,
            messageViewHolderType = MessageViewHolderType.MY_TEXT,
            sendIconUrl = "https://www.billboard.com/files/styles/article_main_image/public/media/Madonna-press-by-Ricardo-Gomes-2019-billboard-1548.jpg",
            isMe = true
        )

        val message5 = ImageMessage(
            id = "4",
            message = "https://steamuserimages-a.akamaihd.net/ugc/952972064286659591/0A22C2617B3DD874D6B18D42ACED0D683DD38EB6/",
            date = DateTime(),
            senderId = "2",
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )
        val messageListItem5 = MessageListItem(
            message = message5,
            messageViewHolderType = MessageViewHolderType.MY_IMAGE,
            sendIconUrl = "https://www.billboard.com/files/styles/article_main_image/public/media/Madonna-press-by-Ricardo-Gomes-2019-billboard-1548.jpg",
            isMe = true
        )

        val message6 = ImageMessage(
            id = "6",
            message = "https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg",
            date = DateTime(),
            senderId = "1",
            messageSendStatus = MessageSendStatus.Sent,
            messageReadStatus = MessageReadStatus.READ
        )
        val messageListItem6 = MessageListItem(
            message = message6,
            messageViewHolderType = MessageViewHolderType.THEIR_IMAGE,
            sendIconUrl = "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTE1ODA0OTcxNzkzMjIxMTMz/sting-9495433-1-402.jpg",
            isMe = false
        )

        results.add(messageListItem1)
        results.add(messageListItem2)
        results.add(messageListItem3)
        results.add(messageListItem4)
        results.add(messageListItem5)
        results.add(messageListItem6)

        onResult(results)
    }

    companion object {
        const val TAG = "ChatPresenter"
    }
}