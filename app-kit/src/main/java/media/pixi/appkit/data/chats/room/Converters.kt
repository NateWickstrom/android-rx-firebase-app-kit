package media.pixi.appkit.data.chats.room

import androidx.room.TypeConverter
import java.util.*


class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return if (date == null) {
            null
        } else {
            date.time
        }
    }

}