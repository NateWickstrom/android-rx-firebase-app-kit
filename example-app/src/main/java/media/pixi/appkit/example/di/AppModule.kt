package media.pixi.appkit.example.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import media.pixi.rx.algolia.search.data.AlgoliaSearchDataSource
import media.pixi.rx.algolia.search.data.SearchProvider
import media.pixi.rx.google.cloud.storage.data.CloudStorageRepo
import media.pixi.rx.google.cloud.storage.data.GoogleCloudStorageDataSource
import javax.inject.Singleton

@Module
abstract class AppModule {

    //expose Application as an injectable context
    @Binds
    internal abstract fun bindContext(application: Application): Context

    @Binds
    internal abstract fun provideSearchProvider(dataSource: AlgoliaSearchDataSource): SearchProvider

    @Singleton
    @Binds
    internal abstract fun provideCloudStorageRepo(dataSource: GoogleCloudStorageDataSource): CloudStorageRepo

    @Module
    companion object {
//        @Singleton
//        @Provides
//        @JvmStatic
//        fun searchDataSource(): SearchProvider {
//            return AlgoliaSearchDataSource()
//        }
    }
}
