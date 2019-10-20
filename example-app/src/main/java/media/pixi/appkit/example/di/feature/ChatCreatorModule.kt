package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.example.di.FragmentScoped
import media.pixi.appkit.ui.chatcreator.ChatCreatorContract
import media.pixi.appkit.ui.chatcreator.ChatCreatorFragment
import media.pixi.appkit.ui.chatcreator.ChatCreatorNavigator
import media.pixi.appkit.ui.chatcreator.ChatCreatorPresenter

@Module
abstract class ChatCreatorModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun chatCreatorFragment(): ChatCreatorFragment

    @ActivityScoped
    @Binds
    internal abstract fun chatCreatorPresenter(presenter: ChatCreatorPresenter): ChatCreatorContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun chatCreatorNavigator(navigator: ChatCreatorNavigator): ChatCreatorContract.Navigator

}
