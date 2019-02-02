package media.pixi.rx.algolia.search.data


import javax.inject.Singleton

import dagger.Binds
import dagger.Module

@Module
abstract class SearchDataModule {

    @Singleton
    @Binds
    internal abstract fun provideSearchProvider(dataSource: AlgoliaSearchDataSource): SearchProvider

}
