package media.pixi.appkit.data.drafts.room

import androidx.room.TypeConverter

import media.pixi.appkit.data.drafts.DraftAttachmentType

class Converters {

    @TypeConverter
    fun fromInt(value: Int): DraftAttachmentType {
        return DraftAttachmentType.toDraftAttachmentType(value)
    }

    @TypeConverter
    fun draftAttachmentTypeToInt(type: DraftAttachmentType): Int {
        return DraftAttachmentType.toId(type)
    }
}
