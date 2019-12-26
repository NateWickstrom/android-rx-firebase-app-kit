package media.pixi.appkit.data.draft

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import media.pixi.appkit.data.drafts.DraftsProvider
import media.pixi.appkit.data.drafts.local.DraftsDao
import media.pixi.appkit.data.drafts.local.DraftsDatabase
import media.pixi.appkit.data.drafts.local.LocalDraftProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DraftsProviderTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DraftsDatabase
    private lateinit var draftsDao: DraftsDao
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

        draftsDao = database.draftsDao()

        provider = LocalDraftProvider(draftsDao)
    }

    @After
    fun closeDb() {
        database.close()
    }
}