package media.pixi.rx.firebase.auth.kit.example.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.di.AuthKitFragmentScoped

@Module
abstract class SplashModule {

    @AuthKitFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity

}
