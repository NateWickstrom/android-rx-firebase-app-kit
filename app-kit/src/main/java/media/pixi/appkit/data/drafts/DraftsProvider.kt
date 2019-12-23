package media.pixi.appkit.data.drafts

import io.reactivex.Completable
import io.reactivex.Maybe

interface DraftsProvider {

    fun getDraft(chatId: String): Maybe<Draft>

    fun deleteDraft(chatId: String): Completable

    fun addToDraft(chatId: String, attachment: DraftAttachment): Completable

    fun setDraft(chatId: String, draft: Draft): Completable

    fun deleteAttachment(attachment: DraftAttachment): Completable

    fun clear(): Completable
}