package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.chatmembers.ChatMembersContract
import media.pixi.appkit.ui.chatmembers.ChatMembersFragment
import media.pixi.appkit.ui.chatmembers.ChatMembersNavigator
import media.pixi.appkit.ui.chatmembers.ChatMembersPresenter

@Module
abstract class ChatMembersModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun chatFragment(): ChatMembersFragment

    @ActivityScoped
    @Binds
    internal abstract fun chatPresenter(presenter: ChatMembersPresenter): ChatMembersContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun chatNavigator(navigator: ChatMembersNavigator): ChatMembersContract.Navigator

}
