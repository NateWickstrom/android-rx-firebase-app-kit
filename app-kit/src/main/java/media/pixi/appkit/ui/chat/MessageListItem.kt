package media.pixi.appkit.ui.chat

import media.pixi.appkit.domain.chats.Message

data class MessageListItem(
    var message: Message,
    var isMe: Boolean = false,
    val sendIconUrl: String = "",
    val messageViewHolderType: MessageViewHolderType,
    var progress: Float = 0.toFloat(),
    val timeInMillis: Long = message.date.toDate().time
)
