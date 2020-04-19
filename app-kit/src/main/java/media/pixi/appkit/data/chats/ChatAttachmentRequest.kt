package media.pixi.appkit.data.chats

import com.google.firebase.Timestamp

data class ChatAttachmentRequest(
    val type: ChatAttachmentType,
    val senderId: String,
    val timestamp: Timestamp,
    val thumbnailUrl: String,
    val fileUrl: String
)