package media.pixi.appkit.domain.chats.models

data class MessageAttachment(
    val thumbnailId: String,
    val fileId: String,
    val type: MessageAttachmentType,
    val thumbnailUrl: String,
    val fileUrl: String
)