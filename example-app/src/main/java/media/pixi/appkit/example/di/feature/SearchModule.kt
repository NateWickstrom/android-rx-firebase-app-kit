package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.search.SearchContract
import media.pixi.appkit.ui.search.SearchFragment
import media.pixi.appkit.ui.search.SearchNavigator
import media.pixi.appkit.ui.search.SearchPresenter

@Module
abstract class SearchModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun searchFragment(): SearchFragment

    @ActivityScoped
    @Binds
    internal abstract fun searchPresenter(presenter: SearchPresenter): SearchContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun searchNavigator(navigator: SearchNavigator): SearchContract.Navigator

}
