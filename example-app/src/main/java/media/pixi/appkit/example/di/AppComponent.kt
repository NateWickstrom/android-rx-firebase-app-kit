package media.pixi.appkit.example.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.config.ConfigProvider
import media.pixi.appkit.data.profile.UserProfileProvider
import media.pixi.appkit.data.search.SearchProvider
import media.pixi.appkit.data.storage.CloudStorageRepo
import media.pixi.appkit.example.App

import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<App> {

    fun getAuthProvider(): AuthProvider
    fun getConfigProvider(): ConfigProvider
    fun getSearchProvider(): SearchProvider
    fun getCloudStorageRepo(): CloudStorageRepo
    fun getCurrentUserProfileProvider(): UserProfileProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
