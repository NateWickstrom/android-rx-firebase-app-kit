package media.pixi.appkit.data.drafts

import io.reactivex.Completable
import io.reactivex.Maybe

interface DraftsProvider {

    fun getDraft(draftId: String): Maybe<Draft>

    // get drafts for chat

    fun deleteDraft(draftId: String): Completable

    fun setDraft(draft: Draft): Completable

    fun addToDraft(draftId: String, attachment: DraftAttachment): Completable

    fun deleteAttachment(attachment: DraftAttachment): Completable

    fun clear(): Completable
}