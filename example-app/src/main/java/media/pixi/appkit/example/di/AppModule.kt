package media.pixi.appkit.example.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.auth.FirebaseAuthProvider
import media.pixi.appkit.data.search.AlgoliaSearchDataSource
import media.pixi.appkit.data.search.SearchProvider
import media.pixi.appkit.data.storage.CloudStorageRepo
import media.pixi.appkit.data.storage.GoogleCloudStorageDataSource
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

    @Singleton
    @Binds
    internal abstract fun provideAuthProvider(dataSource: FirebaseAuthProvider): AuthProvider

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
