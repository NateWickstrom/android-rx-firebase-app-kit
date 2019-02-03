package media.pixi.rx.firebase.auth.kit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.algolia.search.ui.search.SearchContract
import media.pixi.rx.algolia.search.ui.search.SearchFragment
import media.pixi.rx.algolia.search.ui.search.SearchNavigator
import media.pixi.rx.algolia.search.ui.search.SearchPresenter
import media.pixi.rx.firebase.auth.kit.example.di.ActivityScoped
import media.pixi.rx.firebase.auth.kit.example.di.FragmentScoped

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
