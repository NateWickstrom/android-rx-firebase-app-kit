package media.pixi.appkit.ui.chat

import media.pixi.appkit.domain.chats.Message

data class MessageListItem(
    val id: String,
    val message: Message,
    val isMe: Boolean = false,
    val sendIconUrl: String = "",
    val messageViewHolderType: MessageViewHolderType,
    val progress: Float = 0.toFloat(),
    val timeInMillis: Long = message.date.toDate().time,
    val hasSeen: Set<String> = emptySet()
)
