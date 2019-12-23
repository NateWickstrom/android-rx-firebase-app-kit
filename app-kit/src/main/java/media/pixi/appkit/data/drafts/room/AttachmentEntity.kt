package media.pixi.appkit.data.drafts.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Attachments")
class AttachmentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "draftId")
    val draftId: String,

    @ColumnInfo(name = "type")
    val type: Int,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String
)
