package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.friends.FriendsContract
import media.pixi.appkit.ui.friends.FriendsFragment
import media.pixi.appkit.ui.friends.FriendsNavigator
import media.pixi.appkit.ui.friends.FriendsPresenter

@Module
abstract class FriendsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun friendsFragment(): FriendsFragment

    @ActivityScoped
    @Binds
    internal abstract fun friendsPresenter(presenter: FriendsPresenter): FriendsContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun friendsNavigator(navigator: FriendsNavigator): FriendsContract.Navigator

}
