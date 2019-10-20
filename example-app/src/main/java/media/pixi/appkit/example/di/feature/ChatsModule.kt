package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.chats.ChatsContract
import media.pixi.appkit.ui.chats.ChatsFragment
import media.pixi.appkit.ui.chats.ChatsNavigator
import media.pixi.appkit.ui.chats.ChatsPresenter

@Module
abstract class ChatsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun chatsFragment(): ChatsFragment

    @ActivityScoped
    @Binds
    internal abstract fun chatsPresenter(presenter: ChatsPresenter): ChatsContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun chatsNavigator(navigator: ChatsNavigator): ChatsContract.Navigator

}
