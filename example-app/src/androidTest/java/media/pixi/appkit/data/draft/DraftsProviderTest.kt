package media.pixi.appkit.data.draft

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import junit.framework.Assert.assertEquals
import media.pixi.appkit.data.drafts.Draft
import media.pixi.appkit.data.drafts.DraftAttachment
import media.pixi.appkit.data.drafts.DraftAttachmentType
import media.pixi.appkit.data.drafts.DraftsProvider
import media.pixi.appkit.data.drafts.local.DraftsDatabase
import media.pixi.appkit.data.drafts.local.LocalDraftProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DraftsProviderTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DraftsDatabase
    private lateinit var provider: DraftsProvider

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DraftsDatabase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        provider = LocalDraftProvider(database.draftsDao())
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun emptyDrafts() {
        provider.getDraft("123")
            .test()
            .assertNoValues()
    }

    @Test
    fun setAndGetDraft() {
        val draft = Draft(
            id = "123",
            text = "hello world",
            attachments = emptyList()
        )

        provider.setDraft(draft)
            .test()
            .assertComplete()

        provider.getDraft("123")
            .test()
            .assertResult(draft)
    }

    @Test
    fun setMultipleAndGetDraft() {
        val draft1 = Draft(
            id = "123",
            text = "hello world",
            attachments = emptyList()
        )

        val draft2 = Draft(
            id = "456",
            text = "hello world 2",
            attachments = emptyList()
        )

        provider.setDraft(draft1)
            .test()
            .assertComplete()
        provider.setDraft(draft2)
            .test()
            .assertComplete()

        provider.getDraft(draft1.id)
            .test()
            .assertResult(draft1)
    }

    @Test
    fun setGetThenDeleteDraft() {
        val draftId = "123"
        val draft = Draft(
            id = draftId,
            text = "hello world",
            attachments = emptyList()
        )

        provider.setDraft(draft)
            .test()
            .assertComplete()

        provider.getDraft(draftId)
            .test()
            .assertResult(draft)

        provider.deleteDraft(draftId)
            .test()
            .assertComplete()

        provider.getDraft(draftId)
            .test()
            .assertNoValues()
    }

    @Test
    fun setDraftWithAttachment() {
        val attachment = DraftAttachment(
            id = "456",
            type = DraftAttachmentType.IMAGE,
            thumbnailUrl = "some_url",
            fileUrl = "some_other_url"
        )
        val draft = Draft(
            id = "123",
            text = "hello world",
            attachments = listOf(attachment)
        )

        provider.setDraft(draft)
            .test()
            .assertComplete()

        val values = provider.getDraft(draft.id)
            .test().values()

        assertEquals(1, values.size)

        val value = values[0]

        assertEquals(1, value.attachments.size)

        val attach = value.attachments[0]

        assertEquals(attachment, attach)
    }

    @Test
    fun addAttachmentToDraft() {
        val draft = Draft(
            id = "123",
            text = "hello world",
            attachments = emptyList()
        )

        provider.setDraft(draft)
            .test()
            .assertComplete()


        val attachment = DraftAttachment(
            id = "456",
            type = DraftAttachmentType.IMAGE,
            thumbnailUrl = "some_url",
            fileUrl = "some_other_url"
        )

        provider.addToDraft(draft.id, attachment)
            .test()
            .assertComplete()

        val values = provider.getDraft(draft.id)
            .test().values()

        assertEquals(1, values.size)

        val value = values[0]

        assertEquals(1, value.attachments.size)

        val attach = value.attachments[0]

        assertEquals(attachment, attach)
    }

    @Test
    fun deleteAttachmentFromDraft() {
        val attachment = DraftAttachment(
            id = "456",
            type = DraftAttachmentType.IMAGE,
            thumbnailUrl = "some_url",
            fileUrl = "some_other_url"
        )
        val draft = Draft(
            id = "123",
            text = "hello world",
            attachments = listOf(attachment)
        )

        provider.setDraft(draft)
            .test()
            .assertComplete()

        provider.deleteAttachment(attachment)
            .test()
            .assertComplete()

        val values = provider.getDraft(draft.id)
            .test().values()

        assertEquals(1, values.size)

        val value = values[0]

        assertEquals(0, value.attachments.size)
    }

    @Test
    fun clearDrafts() {
        val draftId = "123"
        val draft = Draft(
            id = draftId,
            text = "hello world",
            attachments = emptyList()
        )

        provider.setDraft(draft)
            .test()
            .assertComplete()

        provider.getDraft(draftId)
            .test()
            .assertResult(draft)

        provider.clear()
            .test()
            .assertComplete()

        provider.getDraft(draftId)
            .test()
            .assertNoValues()
    }
}