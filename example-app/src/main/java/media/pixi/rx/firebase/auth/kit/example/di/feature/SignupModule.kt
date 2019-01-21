package media.pixi.rx.firebase.auth.kit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.ActivityScoped
import media.pixi.rx.firebase.auth.kit.example.di.FragmentScoped
import media.pixi.rx.firebase.auth.kit.ui.signup.SignupContract
import media.pixi.rx.firebase.auth.kit.ui.signup.SignupFragment
import media.pixi.rx.firebase.auth.kit.ui.signup.SignupPresenter

@Module
abstract class SignupModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun signupFragment(): SignupFragment

    @ActivityScoped
    @Binds
    internal abstract fun signupPresenter(presenter: SignupPresenter): SignupContract.Presenter
}