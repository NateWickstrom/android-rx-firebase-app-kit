package media.pixi.appkit.data.chats.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Messages")
data class MessageEntity(

    @PrimaryKey
    @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "chatId")
    val chatId: String,

    @ColumnInfo(name = "text")
    val text: String?
)