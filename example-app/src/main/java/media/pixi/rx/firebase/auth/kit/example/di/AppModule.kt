package media.pixi.rx.firebase.auth.kit.example.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import media.pixi.rx.algolia.search.data.AlgoliaSearchDataSource
import media.pixi.rx.algolia.search.data.SearchProvider
import javax.inject.Singleton

@Module
abstract class AppModule {

    //expose Application as an injectable context
    @Binds
    internal abstract fun bindContext(application: Application): Context

    @Binds
    internal abstract fun provideSearchProvider(dataSource: AlgoliaSearchDataSource): SearchProvider

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
