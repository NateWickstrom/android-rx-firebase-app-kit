package media.pixi.rx.firebase.auth.kit.example.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.feature.*
import media.pixi.rx.firebase.auth.kit.example.ui.SplashActivity
import media.pixi.rx.firebase.auth.kit.ui.account.AccountActivity
import media.pixi.rx.firebase.auth.kit.ui.passwordforgot.PasswordForgotActivity
import media.pixi.rx.firebase.auth.kit.ui.passwordupdate.PasswordUpdateActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInActivity
import media.pixi.rx.firebase.auth.kit.ui.signup.SignUpActivity

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AccountModule::class])
    internal abstract fun accountActivity(): AccountActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PasswordForgotModule::class])
    internal abstract fun passwordForgotActivity(): PasswordForgotActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PasswordUpdateModule::class])
    internal abstract fun passwordUpdateActivity(): PasswordUpdateActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SignInModule::class])
    internal abstract fun signInActivity(): SignInActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SignUpModule::class])
    internal abstract fun signUpActivity(): SignUpActivity
}
