package media.pixi.appkit.domain.chats.models

data class MessageAttachment(val id: String, val type: MessageAttachmentType, val thumbnailUrl: String, val fileUrl: String)