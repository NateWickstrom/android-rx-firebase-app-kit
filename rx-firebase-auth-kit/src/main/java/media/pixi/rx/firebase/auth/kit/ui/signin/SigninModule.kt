package media.pixi.rx.firebase.auth.kit.ui.signin

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.di.AuthKitActivityScoped
import media.pixi.rx.firebase.auth.kit.di.AuthKitFragmentScoped

@Module
abstract class SigninModule {

    @AuthKitFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun signinFragment(): SigninFragment

    @AuthKitActivityScoped
    @Binds
    internal abstract fun signinPresenter(presenter: SigninPresenter): SigninContract.Presenter

}
