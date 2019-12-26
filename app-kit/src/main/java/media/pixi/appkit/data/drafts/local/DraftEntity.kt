package media.pixi.appkit.data.drafts.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import media.pixi.appkit.data.drafts.DraftStatus
import java.util.*

@Entity(tableName = "Drafts")
data class DraftEntity(

    @PrimaryKey
    @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "chatId") val chatId: String,

    @ColumnInfo(name = "text")
    val text: String?,

    @ColumnInfo(name = "status")
    val status: DraftStatus = DraftStatus.DRAFT

)