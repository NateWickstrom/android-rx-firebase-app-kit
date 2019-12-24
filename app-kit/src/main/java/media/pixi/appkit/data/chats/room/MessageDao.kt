package media.pixi.appkit.data.chats.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface MessageDao {

    @Query("SELECT * from Messages WHERE id = :id")
    fun getMessageById(id: String): Maybe<MessageEntity>

    @Query("SELECT * from Messages WHERE chatId = :chatId")
    fun getMessageByChatId(chatId: String): Flowable<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: MessageEntity): Completable

    @Query("delete from Messages where id = :id")
    fun deleteMessage(id: String): Completable

    @Query("delete from Messages where chatId = :chatId")
    fun deleteMessagesForChat(chatId: String): Completable

    @Query("DELETE FROM Messages")
    fun deleteAll(): Completable
}