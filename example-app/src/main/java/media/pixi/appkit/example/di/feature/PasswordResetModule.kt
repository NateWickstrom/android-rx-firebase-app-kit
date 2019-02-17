package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.passwordreset.PasswordResetContract
import media.pixi.appkit.ui.passwordreset.PasswordResetFragment
import media.pixi.appkit.ui.passwordreset.PasswordResetNavigator
import media.pixi.appkit.ui.passwordreset.PasswordResetPresenter

@Module
abstract class PasswordResetModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun passwordResetFragment(): PasswordResetFragment

    @ActivityScoped
    @Binds
    internal abstract fun passwordResetPresenter(presenter: PasswordResetPresenter): PasswordResetContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun passwordResetNavigator(navigator: PasswordResetNavigator): PasswordResetContract.Navigator

}
