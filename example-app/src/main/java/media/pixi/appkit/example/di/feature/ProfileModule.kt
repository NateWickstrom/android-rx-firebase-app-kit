package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.myprofile.ProfileContract
import media.pixi.appkit.ui.myprofile.ProfileFragment
import media.pixi.appkit.ui.myprofile.ProfileNavigator
import media.pixi.appkit.ui.myprofile.ProfilePresenter

@Module
abstract class ProfileModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun profileFragment(): ProfileFragment

    @ActivityScoped
    @Binds
    internal abstract fun profilePresenter(presenter: ProfilePresenter): ProfileContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun profileNavigator(navigator: ProfileNavigator): ProfileContract.Navigator

}
