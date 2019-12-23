package media.pixi.appkit.data.drafts

enum class DraftAttachmentType(private val id: Int) {
    IMAGE(0), VIDEO(1);

    companion object {
        fun toDraftAttachmentType(id: Int): DraftAttachmentType {
            return when(id) {
                DraftAttachmentType.IMAGE.id -> IMAGE
                DraftAttachmentType.VIDEO.id -> VIDEO
                else -> throw IllegalArgumentException()
            }
        }

        fun toId(type: DraftAttachmentType): Int {
            return type.id
        }
    }
}