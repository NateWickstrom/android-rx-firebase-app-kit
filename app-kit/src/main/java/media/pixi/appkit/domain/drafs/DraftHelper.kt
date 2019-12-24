package media.pixi.appkit.domain.drafs

import io.reactivex.Completable
import io.reactivex.Maybe
import media.pixi.appkit.data.drafts.Draft
import media.pixi.appkit.data.drafts.DraftAttachment
import media.pixi.appkit.data.drafts.DraftsProvider
import java.io.File
import javax.inject.Inject

class DraftHelper @Inject constructor(private val draftsProvider: DraftsProvider) {

    fun getDraft(chatId: String): Maybe<Draft> {
        return draftsProvider.getDraft(chatId)
            .map { filter(it) }
    }

    fun saveDraft(draft: Draft): Completable {
        return draftsProvider.setDraft(draft)
    }

    fun deleteDraft(draftId: String): Completable {
        return draftsProvider.deleteDraft(draftId)
    }

    private fun filter(draft: Draft): Draft {
        return Draft(
            id = draft.id,
            text = draft.text,
            attachments = draft.attachments.filter { exists(it) }
        )
    }

    private fun exists(attachment: DraftAttachment): Boolean {
        return exists(attachment.thumbnailUrl) && exists(attachment.fileUrl)
    }

    private fun exists(path: String): Boolean {
        return File(path).exists() && File(path).isFile
    }
 }