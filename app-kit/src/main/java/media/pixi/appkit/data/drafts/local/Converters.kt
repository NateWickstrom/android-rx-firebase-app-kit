package media.pixi.appkit.data.drafts.local

import androidx.room.TypeConverter

import media.pixi.appkit.data.drafts.DraftAttachmentType
import media.pixi.appkit.data.drafts.DraftStatus

class Converters {

    @TypeConverter
    fun fromInt(value: Int): DraftAttachmentType {
        return DraftAttachmentType.toDraftAttachmentType(value)
    }

    @TypeConverter
    fun draftAttachmentTypeToInt(type: DraftAttachmentType): Int {
        return DraftAttachmentType.toId(type)
    }

    @TypeConverter
    fun draftStatusfromInt(value: Int): DraftStatus {
        return DraftStatus.toDraftStatus(value)
    }

    @TypeConverter
    fun draftStatusToInt(type: DraftStatus): Int {
        return DraftStatus.toId(type)
    }
}
