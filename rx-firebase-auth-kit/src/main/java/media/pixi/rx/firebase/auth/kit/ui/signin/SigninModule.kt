package media.pixi.rx.firebase.auth.kit.ui.signin

import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.di.AuthKitFragmentScoped
import media.pixi.rx.firebase.auth.kit.ui.account.AccountActivity

@Module
abstract class SigninModule {

    @AuthKitFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun signinActivity(): SigninActivity

}
