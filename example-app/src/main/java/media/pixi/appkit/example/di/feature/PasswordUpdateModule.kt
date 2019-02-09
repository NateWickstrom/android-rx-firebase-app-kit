package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.passwordupdate.PasswordUpdateContract
import media.pixi.appkit.ui.passwordupdate.PasswordUpdateFragment
import media.pixi.appkit.ui.passwordupdate.PasswordUpdateNavigator
import media.pixi.appkit.ui.passwordupdate.PasswordUpdatePresenter

@Module
abstract class PasswordUpdateModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun passwordUpdateFragment(): PasswordUpdateFragment

    @ActivityScoped
    @Binds
    internal abstract fun passwordUpdatePresenter(presenter: PasswordUpdatePresenter): PasswordUpdateContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun passwordUpdateNavigator(navigator: PasswordUpdateNavigator): PasswordUpdateContract.Navigator

}