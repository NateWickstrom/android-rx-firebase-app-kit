package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.chat.ChatContract
import media.pixi.appkit.ui.chat.ChatFragment
import media.pixi.appkit.ui.chat.ChatNavigator
import media.pixi.appkit.ui.chat.ChatPresenter

@Module
abstract class ChatModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun chatFragment(): ChatFragment

    @ActivityScoped
    @Binds
    internal abstract fun chatPresenter(presenter: ChatPresenter): ChatContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun chatNavigator(navigator: ChatNavigator): ChatContract.Navigator

}
