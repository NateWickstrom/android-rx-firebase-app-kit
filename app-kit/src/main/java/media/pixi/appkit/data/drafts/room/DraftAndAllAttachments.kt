package media.pixi.appkit.data.drafts.room

import androidx.room.Embedded
import androidx.room.Relation

data class DraftAndAllAttachments(

    @Embedded
    val draft: DraftEntity,

    @Relation(parentColumn = "id", entityColumn = "draftId")
    val attachments: List<AttachmentEntity>
)