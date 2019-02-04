package media.pixi.rx.firebase.auth.kit.example.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import media.pixi.rx.algolia.search.data.SearchDataModule
import media.pixi.rx.algolia.search.data.SearchProvider
import media.pixi.rx.firebase.auth.kit.data.AuthKitDataModule
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rx.firebase.auth.kit.example.App
import media.pixi.rx.firebase.remote.config.ConfigDataModule
import media.pixi.rx.firebase.remote.config.ConfigProvider

import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ConfigDataModule::class,
    //SearchDataModule::class,
    AuthKitDataModule::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<App> {

    fun getAuthProvider(): AuthProvider
    fun getConfigProvider(): ConfigProvider
    fun getSearchProvider(): SearchProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
