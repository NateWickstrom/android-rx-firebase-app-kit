package media.pixi.rx.firebase.auth.kit.example.di.feature

import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.FragmentScoped
import media.pixi.rx.firebase.auth.kit.example.ui.SplashActivity

@Module
abstract class SplashModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity

}
