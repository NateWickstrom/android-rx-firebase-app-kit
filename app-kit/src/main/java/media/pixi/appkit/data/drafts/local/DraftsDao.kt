package media.pixi.appkit.data.drafts.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface DraftsDao {

    @Query("SELECT * from Drafts WHERE id = :id")
    fun getDraftById(id: String): Maybe<DraftEntity>

    @Query("SELECT * from Attachments WHERE id = :id")
    fun getAttachmentById(id: String): Maybe<AttachmentEntity>

    @Transaction
    @Query("SELECT * from Drafts WHERE id = :id")
    fun getDraftAndAllAttachmentsById(id: String): Maybe<DraftAndAllAttachments>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDraft(user: DraftEntity): Completable

    @Query("UPDATE Drafts SET text = :text WHERE id = :id")
    fun updateDraft(id: String, text: String): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttachment(user: AttachmentEntity): Completable

    @Query("delete from Drafts where id = :id")
    fun deleteDraft(id: String): Completable

    @Query("delete from Attachments where id = :id")
    fun deleteAttachment(id: String): Completable

    @Query("delete from Attachments where draftId = :draftId")
    fun deleteAttachments(draftId: String): Completable

    @Query("delete from Attachments where id in (:ids)")
    fun deleteAttachments(ids: List<String>): Completable

    @Query("DELETE FROM Drafts")
    fun deleteAllDrafts(): Completable

    @Query("DELETE FROM Attachments")
    fun deleteAllAttachments(): Completable
}
