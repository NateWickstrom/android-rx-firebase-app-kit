package media.pixi.appkit.data.chats

import com.google.firebase.Timestamp

data class ChatAttachmentEntity(
    val id: String,
    val type: ChatAttachmentType,
    val thumbnailUrl: String?,
    val fileUrl: String?,
    val senderId: String,
    val timestamp: Timestamp
)