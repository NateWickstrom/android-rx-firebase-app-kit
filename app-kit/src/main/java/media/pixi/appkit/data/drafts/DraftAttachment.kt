package media.pixi.appkit.data.drafts

data class DraftAttachment(
    val id: String,
    val type: DraftAttachmentType,
    val thumbnailUrl: String,
    val fileUrl: String
)
