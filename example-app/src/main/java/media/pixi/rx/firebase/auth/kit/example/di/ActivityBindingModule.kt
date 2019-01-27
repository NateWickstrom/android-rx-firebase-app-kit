package media.pixi.rx.firebase.auth.kit.example.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.feature.*
import media.pixi.rx.firebase.auth.kit.example.ui.home.HomeActivity
import media.pixi.rx.firebase.auth.kit.example.ui.splash.SplashActivity
import media.pixi.rx.firebase.auth.kit.ui.account.AccountActivity
import media.pixi.rx.firebase.auth.kit.ui.passwordforgot.PasswordForgotActivity
import media.pixi.rx.firebase.auth.kit.ui.passwordupdate.PasswordUpdateActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInActivity
import media.pixi.rx.firebase.auth.kit.ui.signup.SignUpActivity
import media.pixi.rx.firebase.profile.kit.ui.profile.ProfileActivity

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun homeActivity(): HomeActivity

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

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    internal abstract fun profileActivity(): ProfileActivity
}
