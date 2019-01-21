package media.pixi.rx.firebase.auth.kit.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.ui.account.PasswordForgotActivity
import media.pixi.rx.firebase.auth.kit.ui.account.PasswordForgotModule
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninModule


@Module
abstract class AuthKitActivityBindingModule {

    @AuthKitActivityScoped
    @ContributesAndroidInjector(modules = [PasswordForgotModule::class])
    internal abstract fun accountActivity(): PasswordForgotActivity

    @AuthKitActivityScoped
    @ContributesAndroidInjector(modules = [SigninModule::class])
    internal abstract fun signinActivity(): SigninActivity

}
