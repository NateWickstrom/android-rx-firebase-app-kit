package media.pixi.appkit.data.chats.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MessageEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {

        @Volatile private var INSTANCE: MessageDatabase? = null

        private const val DB_NAME = "failed_messages.db"

        fun getInstance(context: Context): MessageDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MessageDatabase::class.java, DB_NAME)
                .build()
    }
}