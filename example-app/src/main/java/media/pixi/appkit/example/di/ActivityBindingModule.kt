package media.pixi.appkit.example.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.appkit.example.di.feature.*
import media.pixi.appkit.example.ui.home.HomeActivity
import media.pixi.appkit.example.ui.splash.SplashActivity
import media.pixi.appkit.ui.account.AccountActivity
import media.pixi.appkit.ui.chat.ChatActivity
import media.pixi.appkit.ui.chatcreator.ChatCreatorActivity
import media.pixi.appkit.ui.chatmembers.ChatMembersActivity
import media.pixi.appkit.ui.chats.ChatsActivity
import media.pixi.appkit.ui.develop.DevelopActivity
import media.pixi.appkit.ui.devices.DevicesActivity
import media.pixi.appkit.ui.friends.FriendsActivity
import media.pixi.appkit.ui.imageviewer.ImageViewerActivity
import media.pixi.appkit.ui.myprofile.ProfileActivity
import media.pixi.appkit.ui.notifications.NotificationsActivity
import media.pixi.appkit.ui.passwordforgot.PasswordForgotActivity
import media.pixi.appkit.ui.passwordreset.PasswordResetActivity
import media.pixi.appkit.ui.passwordupdate.PasswordUpdateActivity
import media.pixi.appkit.ui.search.SearchActivity
import media.pixi.appkit.ui.settings.SettingsActivity
import media.pixi.appkit.ui.signin.SignInActivity
import media.pixi.appkit.ui.signup.SignUpActivity
import media.pixi.appkit.ui.userprofile.UserProfileActivity

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ChatMembersModule::class])
    internal abstract fun chatMembersActivity(): ChatMembersActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ChatModule::class])
    internal abstract fun chatActivity(): ChatActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ChatsModule::class])
    internal abstract fun chatsActivity(): ChatsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ChatCreatorModule::class])
    internal abstract fun chatCreatorActivity(): ChatCreatorActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun homeActivity(): HomeActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AccountModule::class])
    internal abstract fun accountActivity(): AccountActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [DevicesModule::class])
    internal abstract fun devicesActivity(): DevicesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [DevelopModule::class])
    internal abstract fun developActivity(): DevelopActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PasswordForgotModule::class])
    internal abstract fun passwordForgotActivity(): PasswordForgotActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PasswordUpdateModule::class])
    internal abstract fun passwordUpdateActivity(): PasswordUpdateActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SignInModule::class])
    internal abstract fun signInActivity(): SignInActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SignUpModule::class])
    internal abstract fun signUpActivity(): SignUpActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    internal abstract fun profileActivity(): ProfileActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SearchModule::class])
    internal abstract fun searchActivity(): SearchActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SettingsModule::class])
    internal abstract fun settingsActivity(): SettingsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [FriendModule::class])
    internal abstract fun friendActivity(): UserProfileActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [FriendsModule::class])
    internal abstract fun friendsActivity(): FriendsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [NotificationsModule::class])
    internal abstract fun notificationsActivity(): NotificationsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PasswordResetModule::class])
    internal abstract fun passwordResetActivity(): PasswordResetActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ImageViewerModule::class])
    internal abstract fun imageViewerActivity(): ImageViewerActivity
}
