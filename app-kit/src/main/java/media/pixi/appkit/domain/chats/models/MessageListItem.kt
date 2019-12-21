package media.pixi.appkit.domain.chats.models

import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.domain.chats.models.Message

data class MessageListItem(
    val id: String,
    val message: Message,
    val isMe: Boolean = false,
    val sendIconUrl: String = "",
    val senderId: String = "",
    val senderProfile: UserProfile?,
    val progress: Float = 0.toFloat(),
    val timeInMillis: Long = message.date.toDate().time,
    val hasSeen: Set<String> = emptySet()
)
