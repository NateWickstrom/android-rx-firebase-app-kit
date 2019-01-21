package media.pixi.rx.firebase.auth.kit.ui.account

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.di.AuthKitActivityScoped
import media.pixi.rx.firebase.auth.kit.di.AuthKitFragmentScoped

@Module
abstract class PasswordForgotModule {

    @AuthKitFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun passwordForgotFragment(): PasswordForgotFragment

    @AuthKitActivityScoped
    @Binds
    internal abstract fun passwordForgotPresenter(presenter: PasswordForgotPresenter): PasswordForgotContract.Presenter

}
