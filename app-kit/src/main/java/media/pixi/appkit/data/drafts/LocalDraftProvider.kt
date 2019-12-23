package media.pixi.appkit.data.drafts

import io.reactivex.Completable
import io.reactivex.Maybe
import media.pixi.appkit.data.drafts.files.FileProvider
import media.pixi.appkit.data.drafts.room.DraftsDao

class LocalDraftProvider(
    private val draftDataSource: DraftsDao,
    private val fileProvider: FileProvider
) : DraftsProvider {

    override fun getDraft(chatId: String): Maybe<Draft> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteDraft(chatId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addToDraft(chatId: String, attachment: DraftAttachment): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setDraftText(chatId: String, text: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFromDraft(chatId: String, attachment: DraftAttachment): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}