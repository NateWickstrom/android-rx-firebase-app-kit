package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.followers.FollowersContract
import media.pixi.appkit.ui.followers.FollowersFragment
import media.pixi.appkit.ui.followers.FollowersNavigator
import media.pixi.appkit.ui.followers.FollowersPresenter

@Module
abstract class FollowersModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun followersFragment(): FollowersFragment

    @ActivityScoped
    @Binds
    internal abstract fun followersPresenter(presenter: FollowersPresenter): FollowersContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun followersNavigator(navigator: FollowersNavigator): FollowersContract.Navigator

}
