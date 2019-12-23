package media.pixi.appkit.data.drafts

import io.reactivex.Completable
import io.reactivex.Maybe

interface DraftsProvider {

    fun getDraft(chatId: String): Maybe<Draft>

    fun deleteDraft(chatId: String): Completable

    fun addToDraft(chatId: String, attachment: DraftAttachment): Completable

    fun setDraftText(chatId: String, text: String): Completable

    fun removeFromDraft(chatId: String, attachment: DraftAttachment): Completable

    fun clear(): Completable
}