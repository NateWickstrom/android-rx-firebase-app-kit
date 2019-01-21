package media.pixi.rx.firebase.auth.kit.example.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.feature.PasswordForgotModule
import media.pixi.rx.firebase.auth.kit.example.di.feature.SigninModule
import media.pixi.rx.firebase.auth.kit.example.ui.SplashActivity
import media.pixi.rx.firebase.auth.kit.example.di.feature.SplashModule
import media.pixi.rx.firebase.auth.kit.ui.passwordforgot.PasswordForgotActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninActivity

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PasswordForgotModule::class])
    internal abstract fun accountActivity(): PasswordForgotActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SigninModule::class])
    internal abstract fun signinActivity(): SigninActivity
}
