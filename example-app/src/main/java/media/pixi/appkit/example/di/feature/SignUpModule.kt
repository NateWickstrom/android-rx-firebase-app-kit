package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.signup.SignUpContract
import media.pixi.appkit.ui.signup.SignUpFragment
import media.pixi.appkit.ui.signup.SignUpNavigator
import media.pixi.appkit.ui.signup.SignUpPresenter

@Module
abstract class SignUpModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun signUpFragment(): SignUpFragment

    @ActivityScoped
    @Binds
    internal abstract fun signUpPresenter(presenter: SignUpPresenter): SignUpContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun signUpNavigator(navigator: SignUpNavigator): SignUpContract.Navigator

}