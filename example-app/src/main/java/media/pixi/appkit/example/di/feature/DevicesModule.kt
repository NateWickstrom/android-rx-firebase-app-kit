package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.devices.DevicesContract
import media.pixi.appkit.ui.devices.DevicesFragment
import media.pixi.appkit.ui.devices.DevicesNavigator
import media.pixi.appkit.ui.devices.DevicesPresenter

@Module
abstract class DevicesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun devicesFragment(): DevicesFragment

    @ActivityScoped
    @Binds
    internal abstract fun devicesPresenter(presenter: DevicesPresenter): DevicesContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun devicesNavigator(navigator: DevicesNavigator): DevicesContract.Navigator

}
