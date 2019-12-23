package media.pixi.appkit.data.drafts.room

import android.content.Context
import androidx.room.*

@Database(entities = [DraftEntity::class, AttachmentEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class DraftsDatabase : RoomDatabase() {

    abstract fun draftsDao(): DraftsDao

    companion object {

        @Volatile private var INSTANCE: DraftsDatabase? = null

        private const val DB_NAME = "messaging_drafts.db"

        fun getInstance(context: Context): DraftsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, DraftsDatabase::class.java, DB_NAME)
                .build()
    }
}