package media.pixi.appkit.data.drafts.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface DraftsDao {

    @Query("SELECT * from Drafts WHERE id = :id")
    fun getDraftById(id: String): Flowable<DraftEntity>

    @Query("SELECT * from Attachments WHERE id = :id")
    fun getAttachmentById(id: String): Flowable<AttachmentEntity>

    @Transaction
    @Query("SELECT * from Drafts WHERE id = :id")
    fun loadDraftAndAllAttachmentsById(id: String): Flowable<DraftAndAllAttachments>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDraft(user: DraftEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttachment(user: AttachmentEntity): Completable

    @Query("DELETE FROM Drafts")
    fun deleteAllDrafts(): Completable

    @Query("DELETE FROM Attachments")
    fun deleteAllAttachments(): Completable
}
