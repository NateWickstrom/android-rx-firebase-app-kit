package media.pixi.appkit.example.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.auth.FirebaseAuthProvider
import media.pixi.appkit.data.config.ConfigProvider
import media.pixi.appkit.data.config.FirebaseConfigDataSource
import media.pixi.appkit.data.devices.DevicesProvider
import media.pixi.appkit.data.devices.FirebaseDevicesProvider
import media.pixi.appkit.data.followers.FirebaseFollowersProvider
import media.pixi.appkit.data.followers.FollowersProvider
import media.pixi.appkit.data.friends.FirebaseFriendsProvider
import media.pixi.appkit.data.friends.FriendsProvider
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.data.profile.FirebaseUserProfileProvider
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

    @Module
    companion object {

        @Singleton
        @Provides
        @JvmStatic
        fun searchSearchProvider(): SearchProvider {
            return AlgoliaSearchDataSource()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideCloudStorageRepo(): CloudStorageRepo {
            return GoogleCloudStorageDataSource()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideAuthProvider(): AuthProvider {
            return FirebaseAuthProvider()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideDevicesProvider(context: Context): DevicesProvider {
            return FirebaseDevicesProvider(context)
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideFollowersProvider(): FollowersProvider {
            return FirebaseFollowersProvider()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideConfigProvider(): ConfigProvider {
            return FirebaseConfigDataSource()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideCurrentUserProfileProvider(): UserProfileProvider {
            return FirebaseUserProfileProvider()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideFriendsProvider(): FriendsProvider {
            return FirebaseFriendsProvider()
        }
    }
}
