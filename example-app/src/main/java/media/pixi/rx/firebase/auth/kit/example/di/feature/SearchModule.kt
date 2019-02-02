package media.pixi.rx.firebase.auth.kit.example.di.feature

import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.algolia.search.ui.search.SearchFragment
import media.pixi.rx.firebase.auth.kit.example.di.FragmentScoped

@Module
abstract class SearchModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun searchFragment(): SearchFragment

}
