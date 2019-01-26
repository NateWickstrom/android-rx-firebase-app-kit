package media.pixi.rx.firebase.auth.kit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.ActivityScoped
import media.pixi.rx.firebase.auth.kit.example.di.FragmentScoped
import media.pixi.rx.firebase.auth.kit.example.ui.AppNavigator
import media.pixi.rx.firebase.profile.kit.ui.profile.*

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
    internal abstract fun profileNavigator(navigator: AppNavigator): ProfileContract.Navigator

}
