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
import media.pixi.appkit.ui.chatoptions.ChatOptionsContract
import media.pixi.appkit.ui.chatoptions.ChatOptionsNavigator
import media.pixi.appkit.ui.chatoptions.ChatOptionsPresenter
import media.pixi.appkit.ui.chatoptionsimage.ChatOptionsImageContract
import media.pixi.appkit.ui.chatoptionsimage.ChatOptionsImageNavigator
import media.pixi.appkit.ui.chatoptionsimage.ChatOptionsImagePresenter
import media.pixi.appkit.ui.chatoptionsvideo.ChatOptionsVideoContract
import media.pixi.appkit.ui.chatoptionsvideo.ChatOptionsVideoNavigator
import media.pixi.appkit.ui.chatoptionsvideo.ChatOptionsVideoPresenter

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

    @ActivityScoped
    @Binds
    internal abstract fun chatOptionsPresenter(presenter: ChatOptionsPresenter): ChatOptionsContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun chatOptionsNavigator(navigator: ChatOptionsNavigator): ChatOptionsContract.Navigator

    @ActivityScoped
    @Binds
    internal abstract fun chatOptionsImagePresenter(presenter: ChatOptionsImagePresenter): ChatOptionsImageContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun chatOptionsImageNavigator(navigator: ChatOptionsImageNavigator): ChatOptionsImageContract.Navigator

    @ActivityScoped
    @Binds
    internal abstract fun chatOptionsVideoPresenter(presenter: ChatOptionsVideoPresenter): ChatOptionsVideoContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun chatOptionsVideoNavigator(navigator: ChatOptionsVideoNavigator): ChatOptionsVideoContract.Navigator
}
