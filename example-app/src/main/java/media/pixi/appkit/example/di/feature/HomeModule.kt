package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.ui.home.HomeContract
import media.pixi.appkit.example.ui.home.HomeNavigator
import media.pixi.appkit.example.ui.home.HomePresenter

@Module
abstract class HomeModule {

    @ActivityScoped
    @Binds
    internal abstract fun homePresenter(presenter: HomePresenter): HomeContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun homeNavigator(navigator: HomeNavigator): HomeContract.Navigator

}
