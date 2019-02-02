package media.pixi.rx.algolia.search.data

import com.algolia.instantsearch.core.helpers.Searcher
import media.pixi.rx.firebase.remote.config.ConfigProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class AlgoliaSearchDataSource @Inject constructor(private val configProvider: ConfigProvider): SearchProvider {

    override fun createUserSearch(): Searcher {
        return Searcher.create(configProvider.getSearchAppId(), configProvider.getSearchApiKey(), CONTACTS)
    }

    companion object {
        private const val CONTACTS = "contacts"
    }
}
