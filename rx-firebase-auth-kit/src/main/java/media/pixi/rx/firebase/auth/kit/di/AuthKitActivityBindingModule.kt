package media.pixi.rx.firebase.auth.kit.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.ui.account.AccountActivity
import media.pixi.rx.firebase.auth.kit.ui.account.AccountModule
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninModule


@Module
abstract class AuthKitActivityBindingModule {

    @AuthKitActivityScoped
    @ContributesAndroidInjector(modules = [AccountModule::class])
    internal abstract fun accountActivity(): AccountActivity

    @AuthKitActivityScoped
    @ContributesAndroidInjector(modules = [SigninModule::class])
    internal abstract fun signinActivity(): SigninActivity

}
