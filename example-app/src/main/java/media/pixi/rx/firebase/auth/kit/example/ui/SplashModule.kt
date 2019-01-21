package media.pixi.rx.firebase.auth.kit.example.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.FragmentScoped

@Module
abstract class SplashModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity

}
