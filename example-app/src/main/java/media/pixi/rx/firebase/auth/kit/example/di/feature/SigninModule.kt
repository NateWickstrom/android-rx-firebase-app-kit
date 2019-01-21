package media.pixi.rx.firebase.auth.kit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.ActivityScoped
import media.pixi.rx.firebase.auth.kit.example.di.FragmentScoped
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninContract
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninFragment
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninNavigator
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninPresenter

@Module
abstract class SigninModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun signinFragment(): SigninFragment

    @ActivityScoped
    @Binds
    internal abstract fun signinPresenter(presenter: SigninPresenter): SigninContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun signinNavigator(presenter: SigninNavigator): SigninContract.Navigator
}
