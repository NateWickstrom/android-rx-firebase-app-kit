package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.develop.DevelopContract
import media.pixi.appkit.ui.develop.DevelopFragment
import media.pixi.appkit.ui.develop.DevelopNavigator
import media.pixi.appkit.ui.develop.DevelopPresenter

@Module
abstract class DevelopModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun developFragment(): DevelopFragment

    @ActivityScoped
    @Binds
    internal abstract fun developPresenter(presenter: DevelopPresenter): DevelopContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun developNavigator(navigator: DevelopNavigator): DevelopContract.Navigator

}
