package media.pixi.appkit.data.drafts

enum class DraftStatus(private val id: Int) {
    DRAFT(0), FAILED(1);

    companion object {
        fun toDraftStatus(id: Int): DraftStatus {
            return when(id) {
                DraftStatus.DRAFT.id -> DRAFT
                DraftStatus.FAILED.id -> FAILED
                else -> throw IllegalArgumentException()
            }
        }

        fun toId(type: DraftStatus): Int {
            return type.id
        }
    }
}