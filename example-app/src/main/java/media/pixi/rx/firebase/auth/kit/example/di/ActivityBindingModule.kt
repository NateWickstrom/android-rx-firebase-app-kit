package media.pixi.rx.firebase.auth.kit.example.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.di.AuthKitActivityScoped
import media.pixi.rx.firebase.auth.kit.example.ui.SplashActivity
import media.pixi.rx.firebase.auth.kit.example.ui.SplashModule

@Module
abstract class ActivityBindingModule {

    @AuthKitActivityScoped
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun splashActivity(): SplashActivity

}
