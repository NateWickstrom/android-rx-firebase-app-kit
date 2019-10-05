package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.userprofile.UserProfileContract
import media.pixi.appkit.ui.userprofile.UserProfileFragment
import media.pixi.appkit.ui.userprofile.UserProfileNavigator
import media.pixi.appkit.ui.userprofile.UserProfilePresenter

@Module
abstract class FriendModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun friendFragment(): UserProfileFragment

    @ActivityScoped
    @Binds
    internal abstract fun friendPresenter(presenter: UserProfilePresenter): UserProfileContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun friendNavigator(navigator: UserProfileNavigator): UserProfileContract.Navigator

}
