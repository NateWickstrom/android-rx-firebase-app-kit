package media.pixi.rx.firebase.auth.kit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.ActivityScoped
import media.pixi.rx.firebase.auth.kit.example.di.FragmentScoped
import media.pixi.rx.firebase.auth.kit.ui.passwordforgot.PasswordForgotContract
import media.pixi.rx.firebase.auth.kit.ui.passwordforgot.PasswordForgotFragment
import media.pixi.rx.firebase.auth.kit.ui.passwordforgot.PasswordForgotNavigator
import media.pixi.rx.firebase.auth.kit.ui.passwordforgot.PasswordForgotPresenter

@Module
abstract class PasswordForgotModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun passwordForgotFragment(): PasswordForgotFragment

    @ActivityScoped
    @Binds
    internal abstract fun passwordForgotPresenter(presenter: PasswordForgotPresenter): PasswordForgotContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun passwordForgotNavigator(presenter: PasswordForgotNavigator): PasswordForgotContract.Navigator

}
