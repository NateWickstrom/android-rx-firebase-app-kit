package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInContract
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInFragment
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInNavigator
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInPresenter

@Module
abstract class SignInModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun signInFragment(): SignInFragment

    @ActivityScoped
    @Binds
    internal abstract fun signInPresenter(presenter: SignInPresenter): SignInContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun signInNavigator(navigator: SignInNavigator): SignInContract.Navigator
}
