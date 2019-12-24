package media.pixi.appkit.data.drafts

import io.reactivex.Completable
import io.reactivex.Maybe
import media.pixi.appkit.data.drafts.room.AttachmentEntity
import media.pixi.appkit.data.drafts.room.DraftAndAllAttachments
import media.pixi.appkit.data.drafts.room.DraftEntity
import media.pixi.appkit.data.drafts.room.DraftsDao

class LocalDraftProvider(private val draftDataSource: DraftsDao) : DraftsProvider {

    override fun getDraft(chatId: String): Maybe<Draft> {
        return draftDataSource.getDraftAndAllAttachmentsById(chatId).map { toDraft(it) }
    }

    override fun deleteDraft(chatId: String): Completable {
        return draftDataSource.deleteDraft(chatId)
            .andThen { draftDataSource.deleteAttachments(chatId) }
    }

    override fun addToDraft(chatId: String, attachment: DraftAttachment): Completable {
        return draftDataSource.insertAttachment(
            AttachmentEntity(
                id = attachment.id,
                draftId = chatId,
                type = attachment.type,
                thumbnailUrl = attachment.thumbnailUrl,
                fileUrl = attachment.fileUrl
            )
        )
    }

    override fun setDraft(draft: Draft): Completable {
        return if (draft.attachments.isEmpty()) {
            setDraftInternal(draft)
        } else {
            setAttachments(draft, draft.attachments)
                .andThen(setDraftInternal(draft))
        }
    }

    private fun setDraftInternal(draft: Draft): Completable {
        return draftDataSource.insertDraft(
            DraftEntity(
                id = draft.id,
                text = draft.text
            )
        )
    }

    private fun setAttachments(draft: Draft, attachments: List<DraftAttachment>): Completable {
        return Completable.merge(attachments.map { setAttachment(draft, it) })
    }

    private fun setAttachment(draft: Draft, attachment: DraftAttachment): Completable {
        return draftDataSource.insertAttachment(
            AttachmentEntity(
                id = attachment.id,
                draftId = draft.id,
                type = attachment.type,
                thumbnailUrl = attachment.thumbnailUrl,
                fileUrl = attachment.fileUrl
            )
        )
    }

    override fun deleteAttachment(attachment: DraftAttachment): Completable {
        return draftDataSource.deleteAttachment(attachment.id)
    }

    override fun clear(): Completable {
        return draftDataSource.deleteAllAttachments()
            .andThen(draftDataSource.deleteAllDrafts())
    }

    private fun toDraft(draftAndAllAttachments: DraftAndAllAttachments): Draft {
        return Draft(
            id = draftAndAllAttachments.draft.id,
            text = draftAndAllAttachments.draft.text,
            attachments = toAttachments(draftAndAllAttachments.attachments)
        )
    }

    private fun toAttachments(attachments: List<AttachmentEntity>): List<DraftAttachment> {
        return attachments.map {
            DraftAttachment(
                id = it.id,
                type = it.type,
                thumbnailUrl = it.thumbnailUrl,
                fileUrl = it.fileUrl
            )
        }
    }
}