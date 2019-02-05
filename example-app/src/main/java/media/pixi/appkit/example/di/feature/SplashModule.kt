package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.ui.splash.SplashContract
import media.pixi.appkit.example.ui.splash.SplashNavigator
import media.pixi.appkit.example.ui.splash.SplashPresenter

@Module
abstract class SplashModule {

    @ActivityScoped
    @Binds
    internal abstract fun splashPresenter(presenter: SplashPresenter): SplashContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun splashNavigator(navigator: SplashNavigator): SplashContract.Navigator

}
