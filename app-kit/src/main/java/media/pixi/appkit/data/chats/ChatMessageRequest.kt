package media.pixi.appkit.data.chats

import com.google.firebase.Timestamp

data class ChatMessageRequest(
    val type: ChatMessageType,
    val text: String,
    val senderId: String,
    val timestamp: Timestamp,
    val thumbnailUrl: String? = null,
    val fileUrl: String? = null
)