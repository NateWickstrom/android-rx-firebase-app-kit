package media.pixi.rx.firebase.auth.kit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.example.di.ActivityScoped
import media.pixi.rx.firebase.auth.kit.example.di.FragmentScoped
import media.pixi.rx.firebase.auth.kit.example.ui.AppNavigator
import media.pixi.rx.firebase.auth.kit.ui.account.AccountContract
import media.pixi.rx.firebase.auth.kit.ui.account.AccountFragment
import media.pixi.rx.firebase.auth.kit.ui.account.AccountNavigator
import media.pixi.rx.firebase.auth.kit.ui.account.AccountPresenter

@Module
abstract class AccountModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun accountFragment(): AccountFragment

    @ActivityScoped
    @Binds
    internal abstract fun accountPresenter(presenter: AccountPresenter): AccountContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun accountNavigator(navigator: AppNavigator): AccountContract.Navigator

}
