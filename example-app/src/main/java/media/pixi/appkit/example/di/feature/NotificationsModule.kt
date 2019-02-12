package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.notifications.NotificationsContract
import media.pixi.appkit.ui.notifications.NotificationsFragment
import media.pixi.appkit.ui.notifications.NotificationsNavigator
import media.pixi.appkit.ui.notifications.NotificationsPresenter

@Module
abstract class NotificationsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun profileFragment(): NotificationsFragment

    @ActivityScoped
    @Binds
    internal abstract fun profilePresenter(presenter: NotificationsPresenter): NotificationsContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun profileNavigator(navigator: NotificationsNavigator): NotificationsContract.Navigator

}
