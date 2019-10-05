package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.example.ui.AppNavigator
import media.pixi.appkit.ui.accountsettings.AccountContract
import media.pixi.appkit.ui.accountsettings.AccountFragment
import media.pixi.appkit.ui.accountsettings.AccountPresenter

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
