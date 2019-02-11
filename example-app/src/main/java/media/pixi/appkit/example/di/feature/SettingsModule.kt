package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.settings.SettingsContract
import media.pixi.appkit.ui.settings.SettingsFragment
import media.pixi.appkit.ui.settings.SettingsNavigator
import media.pixi.appkit.ui.settings.SettingsPresenter

@Module
abstract class SettingsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun settingsFragment(): SettingsFragment

    @ActivityScoped
    @Binds
    internal abstract fun settingsPresenter(presenter: SettingsPresenter): SettingsContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun settingsNavigator(navigator: SettingsNavigator): SettingsContract.Navigator
}
