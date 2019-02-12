package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.friend.FriendContract
import media.pixi.appkit.ui.friend.FriendFragment
import media.pixi.appkit.ui.friend.FriendNavigator
import media.pixi.appkit.ui.friend.FriendPresenter

@Module
abstract class FriendModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun friendFragment(): FriendFragment

    @ActivityScoped
    @Binds
    internal abstract fun friendPresenter(presenter: FriendPresenter): FriendContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun friendNavigator(navigator: FriendNavigator): FriendContract.Navigator

}
