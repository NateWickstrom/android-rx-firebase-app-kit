package media.pixi.appkit.data.drafts.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import media.pixi.appkit.data.drafts.DraftAttachmentType
import java.util.*

@Entity(tableName = "Attachments")
class AttachmentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "draftId")
    val draftId: String,

    @ColumnInfo(name = "type")
    val type: DraftAttachmentType,

    @ColumnInfo(name = "thumbnailUrl")
    val thumbnailUrl: String,

    @ColumnInfo(name = "fileUrl")
    val fileUrl: String
)
