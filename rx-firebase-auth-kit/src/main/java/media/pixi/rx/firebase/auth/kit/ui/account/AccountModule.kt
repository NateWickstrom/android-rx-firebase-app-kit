package media.pixi.rx.firebase.auth.kit.ui.account

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.di.AuthKitActivityScoped
import media.pixi.rx.firebase.auth.kit.di.AuthKitFragmentScoped

@Module
abstract class AccountModule {

    @AuthKitFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun accountFragment(): AccountFragment

    @AuthKitActivityScoped
    @Binds
    internal abstract fun accountPresenter(presenter: AccountPresenter): AccountContract.Presenter

}
