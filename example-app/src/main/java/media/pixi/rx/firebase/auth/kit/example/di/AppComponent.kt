package media.pixi.rx.firebase.auth.kit.example.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import media.pixi.rx.firebase.auth.kit.data.AuthKitDataModule
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rx.firebase.auth.kit.di.AuthKitActivityBindingModule
import media.pixi.rx.firebase.auth.kit.example.App

import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AuthKitDataModule::class,
    ActivityBindingModule::class,
    AuthKitActivityBindingModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<App> {

    fun getAuthProvider(): AuthProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
